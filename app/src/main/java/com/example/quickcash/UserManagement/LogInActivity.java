package com.example.quickcash.UserManagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;

    /**
     * Initialized the login page on load.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeActivity();
    }

    private void initializeActivity() {
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        btnSignUp.setOnClickListener(view -> {
            Intent signUpPageIntent = userManagementAbstractFactory.getIntentInstance
                    (LogInActivity.this, SignUpActivity.class);
            startActivity(signUpPageIntent);
        });
        btnLogin.setOnClickListener(this);
        initializeFirebase();
    }

    private void initializeFirebase() {
        db = FirebaseDatabase.
                getInstance(Constants.FIREBASE_URL);
    }

    /**
     * The method returns the data snapshot from firebase and it calls the method responsible for
     * checking the credentials.
     *
     * @param email    - Email provided by the user.
     * @param password - Password provided by the user.
     */
    private void retrieveDataFromFirebase(String email, String password) {

        DatabaseReference userReference = db.getReference(User.class.getSimpleName());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {
                    try {
                        verifyUserCredentials(dataSnapshot, email, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    /**
     * Finds a user with the given email. If the user exists => checks the password.
     * If the user credentials match => user is redirected to the proper homepage.
     * If the user email or password do not match the user is informed that "Invalid Email or Password."
     *
     * @param dataSnapshot - data from firebase
     * @param email        - email given by the user
     * @param password     - password given by the user
     */
    private void verifyUserCredentials(DataSnapshot dataSnapshot, String email, String password) throws Exception {
        IUser userWithGivenEmail;
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        if (dataSnapshot == null) {
            setStatusMessage("Failed to connect to the database.");
        } else {
            // Find user with the given email and password.
            userWithGivenEmail = getUserFromDataSnapshot(dataSnapshot, email, password);
            // If the user if found => switch to the proper homepage.
            if (userWithGivenEmail == null) {
                setStatusMessage("Invalid Email or Password.");
            } else {
                // Creates login session
                ISessionManager sessionManager = userManagementAbstractFactory.
                        getSessionInstance(this);
                sessionManager.createLoginSession(email, password,
                        userWithGivenEmail.getFirstName() + " " +
                                userWithGivenEmail.getLastName());
                switchToHomePage(userWithGivenEmail.getIsEmployee().equals("y"));
            }
        }
    }

    /**
     * Gets the email and password and calls other methods to ensure their validity.
     *
     * @param loginPage - login page view.
     */
    @Override
    public void onClick(View loginPage) {
        String email = getEmail();
        String password = getPassword();
        if (isEmailEmpty(email)) {
            setStatusMessage(Constants.EMPTY_EMAIL);
        } else if (isPasswordEmpty(password)) {
            setStatusMessage(Constants.EMPTY_PASSWORD);
        } else if (!isProperEmailAddress(email)) {
            setStatusMessage(Constants.IMPROPER_EMAIL_ADDRESS);
        } else {
            retrieveDataFromFirebase(email, password);
            setStatusMessage(Constants.VERIFYING_CREDENTIALS);
        }
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etError);
        etError.setText(statusMessage);
    }

    /**
     * Returns the user with given email.
     *
     * @param dataSnapshot - is the data received from Firebase
     * @param email        - is the email of the user
     * @param password     - password of the user.
     * @return user with the given email, if not user is not found => null.
     */
    protected IUser getUserFromDataSnapshot(DataSnapshot dataSnapshot, String email, String password) throws Exception {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            IUser user = snapshot.getValue(User.class);
            boolean emailMatches = user.getEmail().equals(email);
            boolean passwordMatches = decrypt(user.getPassword()).equals(password);
            if (emailMatches && passwordMatches) {
                return user;
            }
        }
        return null;
    }

    // Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/
    // The below given method was taken from the above mentioned url
    // Date accessed: 17 October,2021

    /**
     * This method is responsible for decrypting the encrypted password string
     *
     * @param encrypted the encrypted password string
     * @return returns decrypted password string
     * @throws Exception To check for NullPointerException
     */
    public String decrypt(String encrypted) throws Exception {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        IAESUtils aesUtils = userManagementAbstractFactory.getAESInstance();
        String decrypted = "";
        try {
            // decrypts the encrypted user password
            decrypted = aesUtils.decrypt(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    /**
     * Switches to the proper homepage
     *
     * @param isEmployee - boolean used to determine which home page to open.
     */
    protected void switchToHomePage(boolean isEmployee) {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        Intent homePageIntent;
        if (isEmployee) {
            homePageIntent = userManagementAbstractFactory.
                    getIntentInstance(this, EmployeeHomeActivity.class);
        } else {
            homePageIntent = userManagementAbstractFactory.
                    getIntentInstance(this, EmployerHomeActivity.class);
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

    protected boolean isProperEmailAddress(String emailAddress) {
        return emailAddress.trim().matches("..*@..*\\...*");
    }
}
