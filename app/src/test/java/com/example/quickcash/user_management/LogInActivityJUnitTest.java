package com.example.quickcash.user_management;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogInActivityJUnitTest {

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
    public void checkIfEmailEmpty() {
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
}
