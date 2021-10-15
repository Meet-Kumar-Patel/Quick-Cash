package com.example.quickcash.UserManagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quickcash.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    boolean found = false;
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        initializeFirebase();
    }

    protected void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(User.class.getSimpleName());

    }


    protected boolean isEmailEmpty(String email){
            return email.trim().isEmpty();
    }

    protected boolean isPasswordEmpty(String password){
        return password.trim().isEmpty();
    }

    protected boolean findEmailInFirebase(String email) {
        initializeFirebase();

        found = false;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User userInFirebase = dataSnapshot.getValue(User.class);
                    String storedEmail = userInFirebase.getEmail();
                    if(storedEmail.equals(email)) {
                        found = true;
                        user = userInFirebase;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Could retrieve: " + error.getCode());
            }
        });

        return found;

    }

    protected  boolean comparePassword(String password) {
        if(user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View loginPage) {
        String email = getEmail();
        String password = getPassword();
        String error = new String("");
        if (isEmailEmpty(email)) {
            error = "Empty Email";
        }
        else if (isPasswordEmpty(password)) {
            error = "Empty Password";
        }
        else if (findEmailInFirebase(email)) {
            error = "Email Found!";
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
