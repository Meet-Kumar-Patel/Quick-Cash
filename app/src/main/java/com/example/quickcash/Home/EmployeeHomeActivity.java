package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.EmployeeDashboardActivity;
import com.example.quickcash.UserManagement.LogInActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.SignUpActivity;
import com.example.quickcash.UserManagement.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class EmployeeHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        Button btnLogOut = findViewById(R.id.btnLogOutEmployee);
        Button btnEmployeeDashBoard = findViewById(R.id.employeeDashButton);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        btnEmployeeDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardPageIntent = new Intent(String.valueOf(EmployeeDashboardActivity.class));
                startActivity(dashboardPageIntent);
            }
        });

        //retrieveDataFromFirebase(com.example.quickcash.UserManagement.SessionManager.KEY_EMAIL);
    }

}
