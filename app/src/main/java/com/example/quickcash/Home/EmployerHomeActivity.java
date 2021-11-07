package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.EmployeeDashboardActivity;
import com.example.quickcash.UserManagement.EmployerDashboardActivity;
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
        Button btnEmployerDashBoard = findViewById(R.id.employerDashButton);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        btnEmployerDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardPageIntent = new Intent(String.valueOf(EmployerDashboardActivity.class));
                startActivity(dashboardPageIntent);
            }
        });
    }

}