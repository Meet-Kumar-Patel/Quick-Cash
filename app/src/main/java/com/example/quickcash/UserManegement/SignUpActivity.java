package com.example.quickcash.UserManegement;

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

// the one that works..
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Make tests First. Follow TDD.

    private EditText firstName;
    private EditText lastName;
    private Button register;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private RadioButton employee;
    private RadioButton employer;
    private EditText phone;
    static boolean userExists = true;
    static FirebaseDatabase db;
    String AES = "AES";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        password = (EditText) findViewById(R.id.txtUserEnteredPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        employee = (RadioButton) findViewById(R.id.radioButton_Employee);
        employer = (RadioButton) findViewById(R.id.radioButton_Employer);

         db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");


        Toast.makeText(SignUpActivity.this, "Welcome to Signup", Toast.LENGTH_LONG).show();

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

    }

    //Pranav:
    protected String getFirstName() {
        /**This method is complete**/
        EditText firstNameBox = findViewById(R.id.txtFirstName);
        return firstNameBox.getText().toString().trim();
    }

    protected String getLastName() {
        EditText lastNameBox = findViewById(R.id.txtLastName);
        return lastNameBox.getText().toString().trim();
    }

    protected String getEmail() {
        EditText emailBox = findViewById(R.id.txtEmail);
        return emailBox.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        EditText phoneNumber = findViewById(R.id.txtPhone);
        return phoneNumber.getText().toString().trim();
    }

    protected boolean isEmailValid(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (emailAddress == null)
            return false;
        else
            return pat.matcher(emailAddress).matches();
    }

    // Implementation: Meet
    protected RadioButton getSelectedRadioButton() {

        employer = findViewById(R.id.radioButton_Employer);
        employee = findViewById(R.id.radioButton_Employee);

        if (employer.isChecked()) {
            return employer;
        } else if (employee.isChecked()) {
            return employee;
        } else {
            return null;
        }
    }

    // Implementation: Meet
    public boolean isEmptyFirstName(String firstName) {
        return firstName.length() <= 0;
    }

    // Implementation: Meet
    public boolean isEmptyLastName(String lastName) {
        return lastName.length() <= 0;
    }

    // Implementation: Meet
    public boolean isEmptyEmail(String email) {
        return email.length() <= 0;
    }

    // Implementation: Meet
    public boolean isEmptyPassword(String password) {
        return password.length() <= 0;
    }

    // Implementation: Meet
    public boolean isEmptyPhoneNumber(String phone) {
        return phone.length() <= 0;
    }

    // Implementation: Meet
    public boolean isEmptyConfirmPassword(String confirmPassword) {
        return confirmPassword.length() <= 0;
    }

    // Implementation: Meet
    public boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10;
    }


    //  Raham:
    protected String getUserEnteredPassword() {
        EditText password1 = (EditText) findViewById(R.id.txtUserEnteredPassword);
        return password1.getText().toString().trim();
    }

    protected String getConfirmPassword() {
        EditText password2 = (EditText) findViewById(R.id.txtConfirmPassword);
        return password2.getText().toString().trim();

    }

    //source for ragex : https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation/3802238
    public boolean isValidPassword(String password1) {
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password1.trim());
        return m.find();
    }


    public boolean isPasswordMatch(String password1, String password2) {
        return password1.equals(password2);

    }

    // Meet and Pranav :

    // Implementation: Meet
    protected void pushToDatabase(String type) throws Exception {

        //initialize the database and the related references
        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtUserEnteredPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        String encryptedPassword = encrypt(password.getText().toString());
        String encryptedConfirmPassword = encrypt(confirmPassword.getText().toString());


        // creates a connection to the database.
        DAOUser user = new DAOUser();

        User user1 = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), phone.getText().toString(), encryptedPassword, encryptedConfirmPassword, type);

        user.add(user1).addOnSuccessListener(saved -> {

            Toast.makeText(SignUpActivity.this, "Firebase Connected! Data Saved",
                    Toast.LENGTH_LONG).show();
        }).addOnFailureListener(failed -> {
            Toast.makeText(SignUpActivity.this, "Data not Saved", Toast.LENGTH_LONG).show();

        });

        switch2Login();
    }


    // Implementation: Meet
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message);
    }

    // Implementation: Meet
    protected void switch2Login() {

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

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

            // to check for email address
            if (isEmptyEmail(emailAddress)) {
                errorMessage = "Please Enter your email address";
                break;

            } else {

                if (!isEmailValid(emailAddress)) {
                    errorMessage = "Please Enter valid email address";
                    break;

                } else {
                    errorMessage = "";
                }
            }

            if (isEmptyPhoneNumber(phone)) {
                errorMessage = "Please enter your phone number";
                break;
            } else {
                if (!isValidPhoneNumber(phone)) {
                    errorMessage = "Phone number should be atleast 10 digits";
                    break;
                } else {
                    errorMessage = "";
                }
            }

            // to check the user Entered password
            if (isEmptyPassword(userEnteredPassword)) {
                errorMessage = "Please Enter your password";
                break;
            } else {

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
                if (isPasswordMatch(userEnteredPassword, confirmPassword)) {
                    errorMessage = "";
                } else {
                    errorMessage = "Passwords don't match. Please Enter again";
                    break;
                }
            }

            // to see that the user has selected one of the two radio buttons
            if (getSelectedRadioButton() == null) {
                errorMessage = "Please Select one of the two given options";
                break;
            } else {
                errorMessage = "";
            }


        }

        setStatusMessage(errorMessage);


        //if error message is empty, then create a new intent to move to main activity
        if (errorMessage.equals("")) {

            String type = "";
            if (employee.isChecked()) {
                type = "Y";
            } else if (employer.isChecked()) {
                type = "N";
            }

            retrieveDataFromFirebase(emailAddress,type);

        }
    }


    protected void retrieveDataFromFirebase(String email,String type) {


        DatabaseReference userReference = db.getReference(User.class.getSimpleName());

        userReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    try {
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


    protected void verifyUserCredentials(DataSnapshot dataSnapshot, String email,String type) throws Exception {

        String statusMessage = new String("");
        User userWithGivenEmail = null;

        if (dataSnapshot == null) {
            statusMessage = "Failed to connect to the database.";
            setStatusMessage(statusMessage);
        } else {

            userWithGivenEmail = getUserFromDataSnapshot(dataSnapshot, email);

            if (userWithGivenEmail == null) {
                pushToDatabase(type);

            }
            else{

               setStatusMessage("User Exists. Please login");

            }

        }


    }

    protected User getUserFromDataSnapshot(DataSnapshot dataSnapshot, String email) {
        System.out.println(dataSnapshot.toString());
        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
            User user = snapshot.getValue(User.class);
            assert user != null;
            if(user.getEmail().equals(email)) {
                return new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getPassword(),
                        user.getConfirmPassword(), user.getIsEmployee());
            }
        }
        return null;
    }


    // Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/
    // The below given method was taken from the above mentioned url
    // Date accessed: 17 October,2021
    protected String encrypt(String password) throws Exception {

        String encrypted = "";
        try {
            encrypted = AESUtils.encrypt(password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    

}
