package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quickcash.UserManegement.LoginActivity;
import com.example.quickcash.UserManegement.SignUpActivity;

public class WelcomePage extends AppCompatActivity {
private Button register;
private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

//      Created two buttons: register and login and their respective event listeners to navigate to those pages
        register = (Button) findViewById(R.id.registerbutton);
        register.setOnClickListener(view -> {navigateToRegPage();});

        login = (Button) findViewById(R.id.loginbutton);
        login.setOnClickListener(view ->{navigateToLoginPage();});
    }


//    Created intents for navigating to login and registration pages
        protected void navigateToRegPage(){
            Intent regPageIntent = new Intent(this, SignUpActivity.class);
            startActivity(regPageIntent);
        }

        protected void navigateToLoginPage(){
            Intent loginPageIntent = new Intent(this, LoginActivity.class);
            startActivity(loginPageIntent);
        }

    }