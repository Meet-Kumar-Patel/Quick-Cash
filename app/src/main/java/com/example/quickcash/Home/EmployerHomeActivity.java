package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;

public class EmployerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        Intent intent = getIntent();
    }
}
