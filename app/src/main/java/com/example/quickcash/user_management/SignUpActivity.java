package com.example.quickcash.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for the implementation of the Sign Up Activity
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    String aes = Constants.AES;                 // String required for encryption
    // The instance variable for the Activity
    private EditText firstName;         // firstName of the user
    private EditText lastName;          // LastName of the user
    private EditText email;             // email of the user
    private EditText password;          // password entered by the user
    private EditText confirmPassword;   // confirm password entered by the user
    private RadioButton employee;       // reference to the YES button
    private RadioButton employer;       // reference to the NO button
    private EditText phone;             // phone number entered by the user

    /**
     * This method is responsible for implementing the initial build of the program.
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate method of every Android Activity. [Reference: https://tanzu.vmware.com/content/blog/android-savedinstancestate-bundle-faq]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // to retrieve all the information from the Sign Up form UI
        initializeActivity();
    }

    private void initializeActivity() {
        Button register;
        Button login;
        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        password = (EditText) findViewById(R.id.txtUserEnteredPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        employee = (RadioButton) findViewById(R.id.radioButton_Employee);
        employer = (RadioButton) findViewById(R.id.radioButton_Employer);
        // Showcasing a message for the user to know that they have entered the app.
        Toast.makeText(SignUpActivity.this, "Welcome to Signup", Toast.LENGTH_LONG).show();
        register = (Button) findViewById(R.id.btnRegister);
        // calling the onClick method whenever the register button is clicked.
        register.setOnClickListener(this);
        login = (Button) findViewById(R.id.buttonLogin);
        // if the login button is clicked instead, the user is transferred to the login page
        login.setOnClickListener(view -> switch2Login());
    }

    /**
     * To retrieve the first Name of the user
     * @return returns the first Name of the user in the form of string.
     */
    protected String getFirstName() {
        /**This method is complete**/
        EditText firstNameBox = findViewById(R.id.txtFirstName);
        return firstNameBox.getText().toString().trim();
    }

    /**
     * To retrieve the last Name of the user
     * @return returns the last Name of the user in the form of string.
     */
    protected String getLastName() {
        EditText lastNameBox = findViewById(R.id.txtLastName);
        return lastNameBox.getText().toString().trim();
    }

    /**
     * To retrieve the email of the user
     * @return returns the email of the user in the form of string.
     */
    protected String getEmail() {
        EditText emailBox = findViewById(R.id.txtEmail);
        return emailBox.getText().toString().trim();
    }

    /**
     * To retrieve the phone of the user
     * @return returns the phone of the user in the form of string.
     */
    protected String getPhoneNumber() {
        EditText phoneNumber = findViewById(R.id.txtPhone);
        return phoneNumber.getText().toString().trim();
    }

    /**
     * To retrieve the user entered password of the user
     * @return returns the user entered password of the user in the form of string.
     */
    protected String getUserEnteredPassword() {
        EditText password1 = (EditText) findViewById(R.id.txtUserEnteredPassword);
        return password1.getText().toString().trim();
    }

    /**
     * To retrieve the confirm password of the user
     * @return returns the confirm password of the user in the form of string.
     */
    protected String getConfirmPassword() {
        EditText password2 = (EditText) findViewById(R.id.txtConfirmPassword);
        return password2.getText().toString().trim();
    }

    /**
     * To check whether the one of the two radio buttons is selected or not.
     * @return returns true if one of the radio button is selected, null otherwise
     */
    protected RadioButton getSelectedRadioButton() {
        employer = findViewById(R.id.radioButton_Employer);
        employee = findViewById(R.id.radioButton_Employee);
        // if the NO radio button is checked
        if (employer.isChecked()) {
            return employer;
            // if the YES radio button is checked
        } else if (employee.isChecked()) {
            return employee;
        } else {
            return null;
        }
    }

    /**
     * To check whether the firstName is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyFirstName(String firstName) {
        return firstName.length() <= 0;
    }

    /**
     * To check whether the lastName is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyLastName(String lastName) {
        return lastName.length() <= 0;
    }

    /**
     * To check whether the email is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyEmail(String email) {
        return email.length() <= 0;
    }

    /**
     * To check whether the password is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyPassword(String password) {
        return password.length() <= 0;
    }

    /**
     * To check whether the phone number is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyPhoneNumber(String phone) {
        return phone.length() <= 0;
    }

    /**
     * To check whether the confirm password is empty or not
     * @return returns true if empty, false otherwise
     */
    public boolean isEmptyConfirmPassword(String confirmPassword) {
        return confirmPassword.length() <= 0;
    }

    /**
     * To check whether the phone number is valid or not
     * @return returns true if of length 10, false otherwise
     */
    public boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10;
    }

    /**
     * To check whether the email is valid
     * @return returns true if the email is valid, false otherwise
     */
    public boolean isEmailValid(String emailAddress) {
        String emailRegex = Constants.EMAIL_REGEX_RULE;
        Pattern pattern = Pattern.compile(emailRegex);
        // to check whether the email address is null
        if (emailAddress == null)
            return false;
        else
            return pattern.matcher(emailAddress).matches();
    }

    /**
     * To check whether the password is valid
     * @return returns true if the password is valid, false otherwise
     */
    //source for regex : https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation/3802238
    public boolean isValidPassword(String password) {
        Pattern pattern = Pattern.
                compile(Constants.REGEX_PASSWORD_RULE);
        Matcher matcher = pattern.matcher(password.trim());
        return matcher.find();
    }

    /**
     * To check whether the password and confirm passwords match
     * @return returns true if the passwords match, false otherwise
     */
    public boolean isPasswordMatch(String password1, String password2) {
        return password1.equals(password2);
    }

    /**
     * This method is responsible for pushing the data to the database
     * @param type The type of user , whether employee or employer
     * @throws Exception To check whether it is not null
     */
    protected void pushToDatabase(String type) throws Exception {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        // encrypt the user entered password
        String encryptedPassword = encrypt(password.getText().toString());
        // encrypt the user entered confirm password
        String encryptedConfirmPassword = encrypt(confirmPassword.getText().toString());
        // creates a new DAOUser object
        DAO userDAO = userManagementAbstractFactory.getUserDAOInstance();
        // create a User object with all the user information
        IUser user = userManagementAbstractFactory.
                getUserInstanceWithParameters(firstName.getText().toString(),
                        lastName.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), encryptedPassword,
                        encryptedConfirmPassword, type);
        // pushes the information entered by the user to the firebase
        userDAO.add(user).addOnSuccessListener(saved ->
                        // Message if push was successful
                        Toast.makeText(SignUpActivity.this,
                                Constants.FIREBASE_CONNECTED_DATA_SAVED,
                                Toast.LENGTH_LONG).show()
                // Message if push was not successful
        ).addOnFailureListener(failed ->
                Toast.makeText(SignUpActivity.this, Constants.DATA_NOT_SAVED,
                        Toast.LENGTH_LONG).show()
        );
        switch2Login();
    }

    /**
     * To set the text of the Status label
     * @param message The message which needs to be set
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message);
    }

    /**
     * To switch from this Activity to Login Activity
     */
    protected void switch2Login() {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        Intent intent = userManagementAbstractFactory.
                getIntentInstance(this, LogInActivity.class);
        startActivity(intent);
    }

    /**
     * The OnClick method is responsible for executing the logic of the Signup
     * @param view The UI of the SignUp activity
     */
    public void onClick(View view) {
        // all the user entered details
        String firstNameStr = getFirstName();
        String lastNameStr = getLastName();
        String emailAddress = getEmail();
        String phoneStr = getPhoneNumber();
        String userEnteredPassword = getUserEnteredPassword();
        String confirmEnteredPassword = getConfirmPassword();
        String errorMessage = "";
        if (isEmptyFirstName(firstNameStr)) {
            errorMessage = Constants.FIRST_NAME_ERROR;
        } else if (isEmptyLastName(lastNameStr)) {
            errorMessage = Constants.LAST_NAME_ERROR;
        } else if (isEmptyEmail(emailAddress)) {
            errorMessage = Constants.EMAIL_ERROR;
        } else if (!isEmailValid(emailAddress)) {
            errorMessage = Constants.INVALID_EMAIL_ADDRESS;
        } else if (isEmptyPhoneNumber(phoneStr)) {
            errorMessage = Constants.PHONE_NUMBER_MANDATORY;
        } else if (!isValidPhoneNumber(phoneStr)) {
            errorMessage = Constants.PHONE_INVALID_ERROR;
        } else if (isEmptyPassword(userEnteredPassword)) {
            errorMessage = "Please Enter your password";
        } else if (!isValidPassword(userEnteredPassword)) {
            errorMessage = "Please Enter valid Password";
        } else if (isEmptyConfirmPassword(confirmEnteredPassword)) {
            errorMessage = "Please Enter to confirm your password";
        } else if (!isPasswordMatch(userEnteredPassword, confirmEnteredPassword)) {
            errorMessage = "Passwords don't match. Please Enter again";
        } else if (getSelectedRadioButton() == null) {
            errorMessage = "Please Select one of the two given options";
        } else {
            errorMessage = "";
        }
        // to set the error message in the status Label
        setStatusMessage(errorMessage);
        //if error message is empty, then create a new intent to move to main activity
        if (errorMessage.equals("")) {
            String type = employee.isChecked() ? "y" : "N";
            retrieveDataFromFirebase(emailAddress, type);
        }
    }

    /**
     * To retrieve information from the firebase
     * @param email email of the user
     * @param type  type of the user
     */
    protected void retrieveDataFromFirebase(String email, String type) {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        // reference to the database
        DatabaseReference userReference = userManagementAbstractFactory.
                getUserDAOInstance().getDatabaseReference();
        // adding event listener to the userReference
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        verifyUserCredentials(dataSnapshot, getEmail(), type);
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
     * This method is responsible for verifying the details of the user
     * @param dataSnapshot The firebase object which contains the data
     * @param email        The email of the user
     * @param type         The type of the user
     * @throws Exception The execption to check for NullPointerException
     */
    protected void verifyUserCredentials(DataSnapshot dataSnapshot, String email, String type) throws Exception {
        String statusMessage = "";
        IUser userWithGivenEmail = null;
        // to see if the firebase object contains data or not.
        if (dataSnapshot == null) {
            statusMessage = "Failed to connect to the database.";
            setStatusMessage(statusMessage);
        } else {
            // to check whether the user already exists or not.
            userWithGivenEmail = getUserFromDataSnapshot(dataSnapshot, email);
            // to push the new user and direct the old user to the login page
            if (userWithGivenEmail == null) {
                pushToDatabase(type);
            } else {
                setStatusMessage("User Exists. Please login");
            }
        }
    }

    /**
     * This method is responsible for going over the data in the database and check whether the user exists or not.
     * @param dataSnapshot The firebase object which contains all the user information
     * @param email        The email of the user
     * @return Returns null if user not found, other wise the user object
     */
    protected IUser getUserFromDataSnapshot(DataSnapshot dataSnapshot, String email) {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        // loop to transverse over the data in the firebase and find the email of the current user.
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            IUser user = snapshot.getValue(User.class);
            // to see if the user is not null
            assert user != null;
            // to check whether the email of the user in firebase matches with the email entered by the user or not.
            if (user.getEmail().equals(email)) {
                // returns the object if it matches, null otherwise.
                return userManagementAbstractFactory.
                        getUserInstanceWithParameters(user.getFirstName(), user.getLastName(),
                                user.getEmail(), user.getPhone(), user.getPassword(),
                                user.getConfirmPassword(), user.getIsEmployee());
            }
        }
        return null;
    }


    // Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/
    // The below given method was taken from the above mentioned url
    // Date accessed: 17 October,2021
    /**
     * This method is responsible for encrypting the user entered password
     * @param password The user entered password
     * @return Returns the encrypted string
     * @throws Exception Check for NullPointerException
     */
    public String encrypt(String password) throws Exception {
        String encrypted = "";
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        try {
            // encrypts the user entered password
            encrypted = userManagementAbstractFactory.getAESInstance().encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    // Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/
    // The below given method was taken from the above mentioned url
    // Date accessed: 17 October,2021
    /**
     * This method is responsible for decrypting the encrypted password string
     * @param encrypted the encrypted password string
     * @return returns decrypted password string
     * @throws Exception To check for NullPointerException
     */
    public String decrypt(String encrypted) throws Exception {
        String decrypted = "";
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        try {
            // decrypts the encrypted user password
            decrypted = userManagementAbstractFactory.getAESInstance().decrypt(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }
}

