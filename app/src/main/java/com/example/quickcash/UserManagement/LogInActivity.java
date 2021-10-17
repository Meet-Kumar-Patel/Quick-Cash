package com.example.quickcash.UserManagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        initializeFirebase();
    }

    protected void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    /**
     * The method returns the data snapshot from firebase and it calls the method responsible for checking the em
     */

    protected void retrieveDataFromFirebase(String email, String password) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());

         userReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // When the data is received, verify the user credential
                    if(dataSnapshot.exists()) {

                        verifyUserCredentials(dataSnapshot, email, password);
                    }
                }
                @Override
                public void onCancelled (@NonNull DatabaseError error){
                    System.out.println("Could retrieve: " + error.getCode());
                }

        });
    }

    /**
     * Finds a user with the given email. If the user exists => checks the password.
     * If the user credentials match => user is redirected to the proper homepage.
     * If the user email or password do not match the user is informed that "Invalid Email or Password."
     * @param dataSnapshot, data from firebase
     * @param email, email given by the user
     * @param password, password given by the user
     */
    protected void verifyUserCredentials(DataSnapshot dataSnapshot, String email, String password) {

        User userWithGivenEmail = null;

        if (dataSnapshot == null) {
            setStatusMessage("Failed to connect to the database.");
        } else {
            // Find user with the given email and password.
            userWithGivenEmail = getUserFromDataSnapshot(dataSnapshot, email, password);

            // If the user if found => switch to the proper homepage.
            if (userWithGivenEmail == null) {
                setStatusMessage("Invalid Email or Password.");
            }
            else {
                switchToHomePage(userWithGivenEmail.getIsEmployee().equals("y"));
            }

        }

    }

    @Override
    public void onClick(View loginPage) {
        String email = getEmail();
        String password = getPassword();

        if (isEmailEmpty(email)) {
            setStatusMessage("Empty Email.");
        } else if (isPasswordEmpty(password)) {
            setStatusMessage("Empty Password.");
        } else if (!isProperEmailAddress(email)){
            setStatusMessage("Improper Email Address");
        } else {
            retrieveDataFromFirebase(email, password);
            setStatusMessage("Verifying credentials");
        }

    }

    public void setStatusMessage(String statusMessage){
        TextView etError = findViewById(R.id.etError);
        etError.setText(statusMessage);
    }

    /**
     * Returns the user with given email.
     * @param dataSnapshot, is the data received from Firebase
     * @param email, is the email of the user
     * @param password, password of the user.
     * @return User with the given email, if not user is not found => null.
     */
    protected User getUserFromDataSnapshot(DataSnapshot dataSnapshot, String email,String password) {
        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
            User user = snapshot.getValue(User.class);
            boolean emailMatches = user.getEmail().equals(email);
            boolean passwordMatches = user.getPassword().equals(password);
            if(emailMatches && passwordMatches) {
                return user;
            }
        }
        return null;
    }

    /**
     * Switches to the proper homepage
     * @param isEmployee, boolean used to determine which home page to open.
     */
    protected void switchToHomePage(boolean isEmployee) {
        Intent homePageIntent;
        if(isEmployee) {
            homePageIntent = new Intent(this, EmployeeHomeActivity.class);
        }
        else {
            homePageIntent = new Intent(this, EmployerHomeActivity.class);
        }
        startActivity(homePageIntent);
    }


    protected String getEmail() {
        EditText etEmail = findViewById(R.id.etEmail);
        return etEmail.getText().toString().trim();
    }

    protected String getPassword() {
        EditText etPassword = findViewById(R.id.etPassword);
        return etPassword.getText().toString().trim();
    }

    protected boolean isEmailEmpty(String email) {
        return email.trim().isEmpty();
    }

    protected boolean isPasswordEmpty(String password) {
        return password.trim().isEmpty();
    }

    public boolean isProperEmailAddress(String emailAddress) {
        return emailAddress.trim().matches("..*@..*\\...*");
    }
}
