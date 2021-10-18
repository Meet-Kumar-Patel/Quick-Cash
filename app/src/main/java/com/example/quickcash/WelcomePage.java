package com.example.quickcash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.UserManagement.LogInActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.SignUpActivity;
import com.google.firebase.FirebaseApp;

public class WelcomePage extends AppCompatActivity {
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        //Created two buttons: register and login and their respective event listeners to navigate to those pages
        register = (Button) findViewById(R.id.registerbutton);
        register.setOnClickListener(view -> {
            navigateToRegPage();
        });
        login = (Button) findViewById(R.id.loginbutton);
        login.setOnClickListener(view -> {
            navigateToLoginPage();
        });
        FirebaseApp.initializeApp(this);
    }

    //Created intents for navigating to login and registration pages
    protected void navigateToRegPage() {
        Intent regPageIntent = new Intent(this, SignUpActivity.class);
        startActivity(regPageIntent);
    }

    protected void navigateToLoginPage() {
        Intent loginPageIntent = new Intent(this, LogInActivity.class);
        startActivity(loginPageIntent);
    }
}