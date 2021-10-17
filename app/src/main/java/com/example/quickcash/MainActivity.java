package com.example.quickcash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.UserManegement.DAOUser;
import com.example.quickcash.UserManegement.LoginActivity;
import com.example.quickcash.UserManegement.SignUpActivity;
import com.example.quickcash.UserManegement.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);
    }

}


