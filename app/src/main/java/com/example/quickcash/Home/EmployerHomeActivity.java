package com.example.quickcash.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        Button btnLogOut = findViewById(R.id.btnLogOutEmployer);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });
    }


    private void retrieveDataFromFirebase(String email) {
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

}