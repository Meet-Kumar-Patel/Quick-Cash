package com.example.quickcash.Paypal;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.AcceptDeclineTasks.AcceptDeclineTasks;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaypalActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 555;
    private static PayPalConfiguration config;
    String username;
    JobPosting jobPostingOBJ;
    ActivityResultLauncher activityResultLauncher;
    ArrayList<Invoice> lstInvoices = new ArrayList<>();
    Button btnPayNow;
    Button btnGoBack;
    TextView txtName;
    TextView txtWage;
    TextView txtDuration;
    TextView txtTotal;
    TextView txtError;
    String amount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID);

        btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(view -> {
            navigateToAcceptDeclinePage();
        });

        Intent intent = getIntent();

        String jobID = intent.getStringExtra("jobID");
        username = intent.getStringExtra("userName");

        btnPayNow = findViewById(R.id.btnPayNow);
        btnGoBack = findViewById(R.id.btnGoBack);
        txtName = findViewById(R.id.txtNameValue);
        txtDuration = findViewById(R.id.txtDurationValue);
        txtTotal = findViewById(R.id.txtTotalValue);
        txtWage = findViewById(R.id.txtWageValue);
        txtError = findViewById(R.id.invoiceErrorMessage);

        retrieveDataFromFirebase(jobID);
        retrieveInvoicesFromFirebase();

        // initializing Activity Launcher
        initializeActivityLauncher();
        btnPayNow.setOnClickListener(v -> processPayment());
    }

    protected void navigateToAcceptDeclinePage() {
        Intent navToAcceptDecline = new Intent(this, AcceptDeclineTasks.class);
        startActivity(navToAcceptDecline);
    }

    protected void retrieveDataFromFirebase(String id) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_URL).getReference(JobPosting.class.getSimpleName());
        jpDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {
                    try {
                        getJPbyID(dataSnapshot, id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }

        });
    }

    protected JobPosting getJPbyID(DataSnapshot dataSnapshot, String id) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            boolean idMatches = jobPosting.getJobPostingId().equals(id);
            if (idMatches) {
                populateLayout(jobPosting);
                jobPostingOBJ = jobPosting;
                return jobPosting;
            }
        }
        return null;
    }

    protected void populateLayout(JobPosting jobPosting) {
        txtName.setText(username);
        txtDuration.setText(String.valueOf(jobPosting.getDuration()));
        txtWage.setText(String.valueOf(jobPosting.getWage()));
        amount = String.valueOf(jobPosting.getDuration() * jobPosting.getWage());
        txtTotal.setText(amount);
    }

    private void initializeActivityLauncher() {
        // Initialize result launcher
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        txtError.setText("Payment " + state + "\n with payment id is " + payID);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }

                //Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.d(TAG, "Launcher Result Invalid");
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                Log.d(TAG, "Launcher Result Cancelled");
            }
        });
    }

    /**
     * This method calls the paypal api to process the payment if the user has not paid already.
     * If the user has already paid then an error message will be displayed informing of the past payment.
     */
    private void processPayment() {
        String jobPostingId = jobPostingOBJ.getJobPostingId();
        Invoice invoice = new Invoice(jobPostingId, amount);
        DAOInvoice daoInvoice = new DAOInvoice();
        if (paymentMade(jobPostingId)) {
            txtError.setText("Sorry, you have already paid.");
        } else {
            daoInvoice.add(invoice);
            // Creating Paypal payment
            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);
            // Creating Paypal Payment activity intent
            Intent intent = new Intent(this, PaymentActivity.class);
            // Adding paypal configuration to the intent
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            // Adding paypal payment to the intent
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            // Starting Activity Request launcher
            activityResultLauncher.launch(intent);
        }
    }

    protected boolean paymentMade(String jobPostingId) {
        for (int i = 0; i < lstInvoices.size(); i++) {
            Invoice invoiceItem = lstInvoices.get(i);
            if (invoiceItem.getJobID().equals(jobPostingId)) return true;
        }
        return false;
    }

    protected void retrieveInvoicesFromFirebase() {
        DatabaseReference invoiceReference = FirebaseDatabase.getInstance(Constants.FIREBASE_URL).getReference(Invoice.class.getSimpleName());
        invoiceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {
                    try {
                        populateLstInvoices(dataSnapshot);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    protected void populateLstInvoices(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Invoice invoice = snapshot.getValue(Invoice.class);
            lstInvoices.add(invoice);
        }
    }
}
