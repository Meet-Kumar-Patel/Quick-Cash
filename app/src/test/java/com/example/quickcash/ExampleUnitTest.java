package com.example.quickcash;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quickcash.UserManegement.SignUpActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    static SignUpActivity signUpActivity;

    @BeforeClass
    public static void setup() {

        signUpActivity = new SignUpActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void checkIfFirstNameIsEmpty() {
        assertTrue(signUpActivity.isEmptyFirstName(""));
        assertFalse(signUpActivity.isEmptyFirstName("Meet"));
    }

    @Test
    public void checkIfLastNameIsEmpty() {
        assertTrue(signUpActivity.isEmptyLastName(""));
        assertFalse(signUpActivity.isEmptyLastName("Patel"));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        assertTrue(signUpActivity.isEmptyEmail(""));
        assertFalse(signUpActivity.isEmptyEmail("pranav.goyal@dal.ca"));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(signUpActivity.isEmptyPassword(""));
        assertFalse(signUpActivity.isEmptyPassword("****"));
    }

}