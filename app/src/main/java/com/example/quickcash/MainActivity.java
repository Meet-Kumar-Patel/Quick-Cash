package com.example.quickcash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.UserManegement.DAOUser;
import com.example.quickcash.UserManegement.User;

public class MainActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Button register;
    private EditText  email;
    private EditText password1;
    private EditText   password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        register = (Button) findViewById(R.id.btnRegister);


        email = (EditText) findViewById(R.id.txtEmail);
        password1 = (EditText) findViewById(R.id.txtPassword1);
        password2 = (EditText) findViewById(R.id.txtPassword2);




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
//Pranav:
    protected String getFirstName(){}
    protected String getLastName(){}
    protected String getEmail(){}
    protected boolean isEmailValid(){}
  //  Raham:
    protected String getPassword1(){}
    protected String getConfirmPassword(){}
    protected boolean isValidPassword(){}
    protected boolean isPasswordMatch(){}

    // Meet and Pranav :
    protected void switch2Login(){}
    protected Task<void> saveFirstNameToFirebase(){}
    protected Task<void> saveLastNameToFirebase(){}
    protected Task<void> saveEmailToFirebase(){}
    protected Task<void> savePasswordToFirebase(){}
    // the method that TA sent goes here


    public void onClick(View view)(){}
    protected void setStatusMessage(String message)(){}









}