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


    //Checks if isEmailEmpty method returns true when no email is given.
    @Test
    public void checkIfEmailEmpty(){
        assertTrue(loginActivity.isEmailEmpty(""));
    }

    //Checks if isProperEmailAddress returns true when given the correct email.
    @Test
    public void checkIfEmailIsAProperEmail() {
        assertTrue(loginActivity.isProperEmailAddress("abc123@dal.ca"));
    }


    //Checks if isPasswordEmpty method returns true when no password is given.
    @Test
    public void checkIfPasswordEmpty() {
        assertTrue(loginActivity.isPasswordEmpty(""));
    }

    // Compare the password
        // if the password is stored in the database => decrypt stored stored password
        // If the entered password is same as the decrypted value => login
        // If the entered password is not the same as the decrypted value => notify "Invalid username/ password invalid".


}
