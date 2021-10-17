package com.example.quickcash.UserManagement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.firebase.database.DataSnapshot;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogInActivityTest {

    static LogInActivity loginActivity;
    @BeforeClass
    public static void setup() {
        loginActivity = new LogInActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
    // All the fields must be filled
        //Check if the email field is empty.
    @Test
    public void checkIfEmailEmpty(){
        assertTrue(loginActivity.isEmailEmpty(""));
        assertFalse(loginActivity.isEmailEmpty("logan123"));
    }

    @Test
    public void checkIfEmailIsAProperEmail() {
        assertTrue(loginActivity.isProperEmailAddress("abc123@dal.ca"));
    }

    // If the password is empty then the user should prompt to fill it.
    @Test
    public void checkIfPasswordEmpty() {
        assertTrue(loginActivity.isPasswordEmpty(""));
        assertFalse(loginActivity.isPasswordEmpty("logan123"));
    }

    // Compare the password
        // if the password is stored in the database => decrypt stored stored password
        // If the entered password is same as the decrypted value => login
        // If the entered password is not the same as the decrypted value => notify "Invalid username/ password invalid".


}
