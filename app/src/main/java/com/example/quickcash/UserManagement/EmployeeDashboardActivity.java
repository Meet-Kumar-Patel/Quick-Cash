package com.example.quickcash.UserManagement;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeDashboardActivity extends AppCompatActivity {

    private static FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_dashboard);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        initializeFirebase();
    }

    private static void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    String statusMessage = "ERROR";
    private void retrieveDataFromFirebase(String email) {
        //db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
        //initializeFirebase();
        DatabaseReference jobReference = db.getReference(JobPosting.class.getSimpleName());
        jobReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, check the job applied by employee
                if (dataSnapshot.exists()) {

                    try {
                        //verifyUserCredentials(dataSnapshot, email);
                        JobPosting jobPosting = getJobPosting(dataSnapshot, email);
                        setStatusMessage(jobPosting.getJobTitle());

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

    public JobPosting getJobPosting(DataSnapshot dataSnapshot, String email) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            if(jobPosting != null) {
                boolean emailMatches = jobPosting.getAccepted().equals(email);
                if (emailMatches) {
                    return jobPosting;
                }
            }
        }
        return null;
    }

    private void setStatusMessage(String statusMessage) {
        EditText etError = findViewById(R.id.employeeMultiLine);
        etError.setText(statusMessage);
    }

    /*public String getStatusMessage() {
        return statusMessage;
    }*/
}
