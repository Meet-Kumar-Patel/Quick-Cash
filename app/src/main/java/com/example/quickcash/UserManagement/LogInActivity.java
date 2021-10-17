package com.example.quickcash.UserManagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    static String[] arrayUserEmails = new String[10];
    User loggedInUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        //initializeFirebase();
    }

    protected void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");

    }


    protected boolean isEmailEmpty(String email) {
        return email.trim().isEmpty();
    }

    protected boolean isPasswordEmpty(String password) {
        return password.trim().isEmpty();
    }

    static String found = "false";

    protected User GetUser(String email) {
        return new User();
    }
    DataSnapshot testSnapshot;
    protected boolean retireveDataFromFirebase(String email) {
        FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
        List<User> userList = new ArrayList<>();
        databaseReference.getReference(User.class.getSimpleName()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    isEmailInFirebase(dataSnapshot);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                System.out.println("Could retrieve: " + error.getCode());
            }

    });
        return true;
}

    protected boolean comparePassword(String password) {
        if (loggedInUser.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    String emailInClass = "";
    @Override
    public void onClick(View loginPage) {
        String email = getEmail();
        String password = getPassword();
        String error = new String("");
        if (isEmailEmpty(email)) {
            error = "Empty Email.";
        } else if (isPasswordEmpty(password)) {
            error = "Empty Password.";
        }
        if(error.equals("")) {
            emailInClass = email;
            retireveDataFromFirebase(email);
            error = "Verifying credentials";
        }



        TextView etError = findViewById(R.id.etError);
        etError.setText(error);

    }

    protected void isEmailInFirebase(DataSnapshot dataSnapshot) {
        String error = new String("");
        if (dataSnapshot == null) {
            error = "Failed to connect to the database.";
        } else {
            for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                User user = snapshot.getValue(User.class);
                if(user.getEmail().equals(emailInClass))
                    loggedInUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getPassword(),
                            user.getConfirmPassword(), user.getIsEmployee());
            }
        }

        if(loggedInUser == null) {
            error = "Invalid Email or Password.";
        }

        TextView etError = findViewById(R.id.etError);
        etError.setText(error);
    }




    protected String getEmail() {
        EditText etEmail = findViewById(R.id.etEmail);
        return etEmail.getText().toString().trim();
    }

    protected String getPassword() {
        EditText etPassword = findViewById(R.id.etPassword);
        return etPassword.getText().toString().trim();
    }
}
