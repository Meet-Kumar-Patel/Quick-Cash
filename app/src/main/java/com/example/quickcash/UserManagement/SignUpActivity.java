package com.example.quickcash.UserManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for the implementation of the Sign Up Activity
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    // The instance variable for the Activity
    private EditText firstName;         // firstName of the user
    private EditText lastName;          // LastName of the user
    private Button register;            // reference to the register button
    private Button login;               // reference to the login button
    private EditText email;             // email of the user
    private EditText password;          // password entered by the user
    private EditText confirmPassword;   // confirm password entered by the user
    private RadioButton employee;       // reference to the YES button
    private RadioButton employer;       // reference to the NO button
    private EditText phone;             // phone number entered by the user
    static boolean userExists = true;   // boolean to check whether the user Exists or not.
    static FirebaseDatabase db;         // reference to the fireBase.
    String AES = "AES";                 // String required for encryption


    /**
     * This method is responsible for implementing the initial build of the program.
     * @param savedInstanceState  reference to a Bundle object that is passed into the onCreate method of every Android Activity. [Reference: https://tanzu.vmware.com/content/blog/android-savedinstancestate-bundle-faq]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // to retrieve all the information from the Sign Up form UI
        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        password = (EditText) findViewById(R.id.txtUserEnteredPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        employee = (RadioButton) findViewById(R.id.radioButton_Employee);
        employer = (RadioButton) findViewById(R.id.radioButton_Employer);


        // establishing a connection to the firebase database.
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");

        // Showcasing a message for the user to know that they have entered the app.
        Toast.makeText(SignUpActivity.this, "Welcome to Signup", Toast.LENGTH_LONG).show();

        register = (Button) findViewById(R.id.btnRegister);

        // calling the onClick method whenever the register button is clicked.
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.buttonLogin);

        // if the login button is clicked instead, the user is transferred to the login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Login();
            }
        });

    }

    /**
     * To retrive the first Name of the user
     * @return returns the first Name of the user in the form of string.
     */
    protected String getFirstName() {
        /**This method is complete**/
        EditText firstNameBox = findViewById(R.id.txtFirstName);
        return firstNameBox.getText().toString().trim();
    }

    /**
     * To retrive the last Name of the user
     * @return returns the last Name of the user in the form of string.
     */
    protected String getLastName() {
        EditText lastNameBox = findViewById(R.id.txtLastName);
        return lastNameBox.getText().toString().trim();
    }

    /**
     * To retrive the email of the user
     * @return returns the email of the user in the form of string.
     */
    protected String getEmail() {
        EditText emailBox = findViewById(R.id.txtEmail);
        return emailBox.getText().toString().trim();
    }

    /**
     * To retrive the phone of the user
     * @return returns the phone of the user in the form of string.
     */
    protected String getPhoneNumber() {
        EditText phoneNumber = findViewById(R.id.txtPhone);
        return phoneNumber.getText().toString().trim();
    }

    /**
     * To retrive the user entered password of the user
     * @return returns the user entered password of the user in the form of string.
     */
    protected String getUserEnteredPassword() {
        EditText password1 = (EditText) findViewById(R.id.txtUserEnteredPassword);
        return password1.getText().toString().trim();
    }

    /**
     * To retrive the confirm password of the user
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
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        // to check whether the email address is null
        if (emailAddress == null)
            return false;
        else
            return pat.matcher(emailAddress).matches();
    }

    /**
     * To check whether the password is valid
     * @return returns true if the password is valid, false otherwise
     */
    //source for ragex : https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation/3802238
    public boolean isValidPassword(String password1) {
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password1.trim());
        return m.find();
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

        //initialize the database and the related references
        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtUserEnteredPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        // encrypt the user entered password
        String encryptedPassword = encrypt(password.getText().toString());

        // encrypt the user entered confirm password
        String encryptedConfirmPassword = encrypt(confirmPassword.getText().toString());


        // creates a new DAOUser object
        DAOUser user = new DAOUser();

        // create a User object with all the user information
        User user1 = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), phone.getText().toString(), encryptedPassword, encryptedConfirmPassword, type);

        // pushes the information entered by the user to the firebase
        user.add(user1).addOnSuccessListener(saved -> {

            // Message if push was successful
            Toast.makeText(SignUpActivity.this, "Firebase Connected! Data Saved",
                    Toast.LENGTH_LONG).show();

            // Message if push was not successful
        }).addOnFailureListener(failed -> {
            Toast.makeText(SignUpActivity.this, "Data not Saved", Toast.LENGTH_LONG).show();

        });

        // switch to the login page
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

        Intent intent = new Intent(this, LogInActivity.class);

        startActivity(intent);
    }

    /**
     * The OnClick method is responsible for executing the logic of the Signup
     * @param view The UI of the SignUp activity
     */
    public void onClick(View view) {

        // all the user entered details
        String firstName = getFirstName();
        String lastName = getLastName();
        String emailAddress = getEmail();
        String phone = getPhoneNumber();
        String userEnteredPassword = getUserEnteredPassword();
        String confirmPassword = getConfirmPassword();
        String errorMessage = new String("ERROR MESSAGE");


        // Sign Up logic

        // loop to check whether all the information is added correctly or not.
        for (int i = 0; i < 1; i++) {

            // to check for empty first Name
            if (isEmptyFirstName(firstName)) {
                errorMessage = "Please Enter Your First Name";
                break;

            } else {
                errorMessage = "";
            }

            // to check for empty last name
            if (isEmptyLastName(lastName)) {
                errorMessage = "Please Enter Your Last Name";
                break;

            } else {
                errorMessage = "";
            }

            // to check for empty email address
            if (isEmptyEmail(emailAddress)) {
                errorMessage = "Please Enter your email address";
                break;

            } else {

                // to check for valid email address
                if (!isEmailValid(emailAddress)) {
                    errorMessage = "Please Enter valid email address";
                    break;

                } else {
                    errorMessage = "";
                }
            }

            // to check for empty phone number
            if (isEmptyPhoneNumber(phone)) {
                errorMessage = "Please enter your phone number";
                break;

            } else {

                // to check for valid phone number
                if (!isValidPhoneNumber(phone)) {
                    errorMessage = "Phone number should be atleast 10 digits";
                    break;

                } else {
                    errorMessage = "";
                }
            }

            // to check for empty user entered password
            if (isEmptyPassword(userEnteredPassword)) {
                errorMessage = "Please Enter your password";
                break;

            } else {

                // to check for valid user entered password
                if (isValidPassword(userEnteredPassword)) {
                    errorMessage = "";
                } else {
                    errorMessage = "Please Enter valid Password";
                    break;
                }
            }

            // to check the confirm Password
            if (isEmptyConfirmPassword(confirmPassword)) {
                errorMessage = "Please Enter to confirm your password";
                break;
            } else {

                // to check whether the user entered password and confirm password match or not.
                if (isPasswordMatch(userEnteredPassword, confirmPassword)) {
                    errorMessage = "";

                } else {
                    errorMessage = "Passwords don't match. Please Enter again";
                    break;
                }
            }

            // to check that the user has selected one of the two radio buttons
            if (getSelectedRadioButton() == null) {
                errorMessage = "Please Select one of the two given options";
                break;

            } else {
                errorMessage = "";
            }

        }

        // to set the error message in the status Label
        setStatusMessage(errorMessage);


        //if error message is empty, then create a new intent to move to main activity
        if (errorMessage.equals("")) {

            String type = "";

            // to find the tyoe of user
            if (employee.isChecked()) {
                type = "Y";

            } else if (employer.isChecked()) {
                type = "N";
            }

            // to check whether the user already exists or not.
            retrieveDataFromFirebase(emailAddress,type);

        }
    }

    /**
     * To retrieve information from the firebase
     * @param email email of the user
     * @param type type of the user
     */
    protected void retrieveDataFromFirebase(String email,String type) {

        // reference to the database
        DatabaseReference userReference = db.getReference(User.class.getSimpleName());

        // adding event listener to the userReference
        userReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // to see if there is any data in the database
                if(dataSnapshot.exists()) {
                    try {

                        // verifying the user credentials using the email and type of user
                        verifyUserCredentials(dataSnapshot, getEmail(),type);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                System.out.println("Could retrieve: " + error.getCode());
            }

        });

    }

    /**
     * This method is responsible for verifying the details of the user
     * @param dataSnapshot The firebase object which contains the data
     * @param email The email of the user
     * @param type The type of the user
     * @throws Exception The execption to check for NullPointerException
     */
    protected void verifyUserCredentials(DataSnapshot dataSnapshot, String email,String type) throws Exception {

        String statusMessage = new String("");

        User userWithGivenEmail = null;

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

            }
            else{

                setStatusMessage("User Exists. Please login");

            }

        }


    }

    /**
     * This method is responsible for going over the data in the database and check whether the user exists or not.
     * @param dataSnapshot The firebase object which contains all the user information
     * @param email The email of the user
     * @return  Returns null if user not found, other wise the user object
     */
    protected User getUserFromDataSnapshot(DataSnapshot dataSnapshot, String email) {

        System.out.println(dataSnapshot.toString());

        // loop to transverse over the data in the firebase and find the email of the current user.
        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

            User user = snapshot.getValue(User.class);

            // to see if the user is not null
            assert user != null;

            // to check whether the email of the user in firebase matches with the email entered by the user or not.
            if(user.getEmail().equals(email)) {

                // returns the object if it matches, null otherwise.
                return new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getPassword(),
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
     * @return  Returns the encrypted string
     * @throws Exception Check for NullPointerException
     */
    public String encrypt(String password) throws Exception {

        String encrypted = "";

        try {

            // encrypts the user entered password
            encrypted = AESUtils.encrypt(password);

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

        try {

            // decrypts the encrypted user password
            decrypted = AESUtils.decrypt(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }



}

