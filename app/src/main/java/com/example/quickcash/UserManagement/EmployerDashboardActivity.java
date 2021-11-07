package com.example.quickcash.UserManagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployerDashboardActivity extends AppCompatActivity {

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_dashboard);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        initializeFirebase();

    }

    private void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

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
                        System.out.println(jobPosting);

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

    private JobPosting getJobPosting(DataSnapshot dataSnapshot, String email) throws Exception {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            boolean emailMatches = jobPosting.getAccepted().equals(email);
            if (emailMatches) {
                return jobPosting;
            }
        }
        return null;
    }

}
