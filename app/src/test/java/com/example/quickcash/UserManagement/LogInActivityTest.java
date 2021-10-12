package com.example.quickcash.UserManagement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class LogInActivityTest {

    static LogInActivity loginActivity;

    @BeforeClass
    public static void setup() {
        loginActivity = new LogInActivity();
    }

    // All the fields must be filled
        //Check if the username field is empty.
    @Test
    public void checkIfUsernameEmpty(){
        assertTrue(loginActivity.isUsernameEmpty(""));
        assertFalse(loginActivity.isUsernameEmpty("logan123"));
    }
    // If the password is empty then the user should prompt to fill it.
    @Test
    public void checkIfPasswordEmpty() {
        assertTrue(loginActivity.isPasswordEmpty(""));
        assertFalse(loginActivity.isPasswordEmpty("logan123"));
    }

    // Check if the user name exist in the firebase
        // if the username E in the firebase => check if the password is valid and login
        // if the username does not E in the firebase => notify "Username/password invalid".

    // Compare the password
        // if the password is stored in the database => decrypt stored stored password
        // If the entered password is same as the decrypted value => login
        // If the entered password is not the same as the decrypted value => notify "Invalid username/ password invalid".


}
