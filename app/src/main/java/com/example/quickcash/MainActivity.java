package com.example.quickcash;

//import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.UserManegement.DAOUser;
import com.example.quickcash.UserManegement.User;
import java.util.regex.Matcher;


import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Button register;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        register = (Button) findViewById(R.id.btnRegister);


        email = (EditText) findViewById(R.id.txtEmail);


        DAOUser daoUser = new DAOUser();
        register.setOnClickListener(v -> {
            User user = new User(firstName.getText().toString(), lastName.getText().toString());
            daoUser.add(user).addOnSuccessListener(saved -> {
                Toast.makeText(MainActivity.this, "Firebase Connected! Data Saved",
                        Toast.LENGTH_LONG).show();
            }).addOnFailureListener(failed -> {
                Toast.makeText(MainActivity.this, "Data not Saved", Toast.LENGTH_LONG).show();
            });
        });
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

    protected boolean isEmptyFirstName()

    //  Raham:
    protected String getPassword1() {
        EditText password1 = (EditText) findViewById(R.id.txtPassword1);
        return password1.getText().toString().trim();
    }

    protected String getConfirmPassword() {
        EditText password2 = (EditText) findViewById(R.id.txtPassword2);
        return password2.getText().toString().trim();

    }
//source for ragex : https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation/3802238
    protected boolean isValidPassword(String password1) {
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password1.trim());
        return m.find();
    }


    protected boolean isPasswordMatch(String password1,String password2) {
        return password1.equals(password2);

    }

    // Meet and Pranav :
    protected void switch2Login() {
    }

    protected Task<void> saveFirstNameToFirebase() {
    }

    protected Task<void> saveLastNameToFirebase() {
    }

    protected Task<void> saveEmailToFirebase() {
    }

    protected Task<void> savePasswordToFirebase() {
    }
    // the method that TA sent goes here


    public void onClick(View view) {
        String firstName1 = getFirstName();
        String lastName1 = getLastName();
        String emailAddress = getEmail();

        String errorMessage = new String("ERROR MESSAGE");
        if (isEmptyBannerID(bannerID)) {
            errorMessage = getResources().getString(R.string.EMPTY_BANNER_ID).trim();
            System.out.println(errorMessage);
        } else if (isValidBannerID(bannerID)) {
            errorMessage = "";
            if (isValidEmailAddress(emailAddress)) {
                errorMessage = "";
                if (!isDALEmailAddress(emailAddress)) {
                    errorMessage = getResources().getString(R.string.INVALID_DAL_EMAIL);
                    //System.out.println(errorMessage);
                }
            } else {
                errorMessage = getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
                //System.out.println(errorMessage);
            }

        } else {
            errorMessage = "Invalid Banner ID";
        }


        //This method is incomplete, your business logic goes here!
        setStatusMessage(errorMessage);

        //if error message is empty, then create a new intent to move to main activity
        if (errorMessage.equals("")) {
            saveBannerIDToFirebase(bannerID);
            saveEmailToFirebase(emailAddress);
            switch2WelcomeWindow(bannerID, emailAddress);
        }
    }

    protected void setStatusMessage(String message)()

    {
    }


}
}