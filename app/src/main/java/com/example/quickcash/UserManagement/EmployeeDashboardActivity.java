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

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_dashboard);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        initializeFirebase();
    }

    private void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    String statusMessage = "ERROR";
    public void retrieveDataFromFirebase(String email) {
        DatabaseReference jobReference = FirebaseDatabase.getInstance().getReference(JobPosting.class.getSimpleName());
        jobReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
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

    protected JobPosting getJobPosting(DataSnapshot dataSnapshot, String email) throws Exception {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            boolean emailMatches = jobPosting.getAccepted().equals(email);
            if (emailMatches) {
                return jobPosting;
            }
        }
        return null;
    }

    private void setStatusMessage(String statusMessage) {
        EditText etError = findViewById(R.id.employeeMultiLine);
        etError.setText(statusMessage);
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
