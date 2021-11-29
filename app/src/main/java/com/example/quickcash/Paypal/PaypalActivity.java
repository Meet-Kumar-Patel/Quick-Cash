package com.example.quickcash.Paypal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quickcash.AcceptDeclineTasks.AcceptDeclineTasks;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.preferencePage;
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

public class PaypalActivity extends AppCompatActivity {

    String username;

    ActivityResultLauncher activityResultLauncher;

    private static final int PAYPAL_REQUEST_CODE = 555;
    private static PayPalConfiguration config;

    Button btnPayNow;
    Button btnGoBack;
    TextView txtName;
    TextView txtWage;
    TextView txtDuration;
    TextView txtTotal;
    TextView txtError;
    String amount = "";


    protected void navigateToAcceptDeclinePage() {
        Intent navToAcceptDecline = new Intent(this, AcceptDeclineTasks.class);
        startActivity(navToAcceptDecline);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID);

        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(view -> {
            navigateToAcceptDeclinePage();
        });

        Intent intent = getIntent();

        String jobID = intent.getStringExtra("jobID");
        username = intent.getStringExtra("username");

        btnPayNow = findViewById(R.id.btnPayNow);
        btnGoBack = findViewById(R.id.btnGoBack);
        txtName = findViewById(R.id.txtNameValue);
        txtDuration = findViewById(R.id.txtDurationValue);
        txtTotal = findViewById(R.id.txtTotalValue);
        txtError = findViewById(R.id.invoiceErrorMessage);

        retrieveDataFromFirebase(jobID);

        // initializing Activity Launcher
        initializeActivityLauncher();

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }

        });

    }
    protected void retrieveDataFromFirebase(String id) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/").getReference(JobPosting.class.getSimpleName());
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
                System.out.println("Could retrieve: " + error.getCode());
            }

        });
    }
JobPosting jobPostingOBJ;
    protected JobPosting getJPbyID(DataSnapshot dataSnapshot, String id) throws Exception {
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
        // Get the layout views


        txtName.setText(username);
        txtDuration.setText(jobPosting.getDuration());
        txtWage.setText(jobPosting.getWage());
        amount = String.valueOf(jobPosting.getDuration() * jobPosting.getWage());
        txtTotal.setText(amount);

    }

    private void initializeActivityLauncher() {
        // Initialize result launcher
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
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
                } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d(TAG,"Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Launcher Result Cancelled");
                }
            }
        });
    }

    private void processPayment() {
        // Creating Paypal payment
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"CAD","Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        // Adding paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }
}
