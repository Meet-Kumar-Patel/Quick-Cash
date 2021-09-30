package com.example.quickcash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        register = (Button) findViewById(R.id.btnRegister);

        DAOUser daoUser = new DAOUser();
        register.setOnClickListener( v -> {
            User user = new User(firstName.getText().toString(), lastName.getText().toString());
            daoUser.add(user).addOnSuccessListener(saved -> {
                Toast.makeText(MainActivity.this, "Firebase Connected! Data Saved",
                        Toast.LENGTH_LONG).show();}).addOnFailureListener(failed -> {
                            Toast.makeText(MainActivity.this, "Data not Saved",Toast.LENGTH_LONG).show();
            });
        });
    }




}