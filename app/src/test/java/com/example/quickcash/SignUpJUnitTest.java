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
public class SignUpJUnitTest {

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

    @Test
    public void checkIfConfirmPasswordIsEmpty() {
        assertTrue(signUpActivity.isEmptyConfirmPassword(""));
        assertFalse(signUpActivity.isEmptyConfirmPassword("****"));
    }

    @Test
    public void checkIfPhoneNumberIsEmpty() {
        assertTrue(signUpActivity.isEmptyPhoneNumber(""));
        assertFalse(signUpActivity.isEmptyPhoneNumber("123456"));
    }

    @Test
    public void checkIfValidPhoneNumber(){
        assertTrue(signUpActivity.isValidPhoneNumber("1234567890"));
        assertFalse(signUpActivity.isValidPhoneNumber("21334"));
    }

    @Test
    public void checkIfPasswordIsValid() {
        assertTrue(signUpActivity.isValidPassword("Ab1!azasb2#2121"));
        assertFalse(signUpActivity.isValidPassword("AAAAAAAAAAAAA"));
        assertFalse(signUpActivity.isValidPassword("1Azsxs"));
        assertFalse(signUpActivity.isValidPassword("1A!z"));
        assertFalse(signUpActivity.isValidPassword("Azazaxsdxs"));
        assertFalse(signUpActivity.isValidPassword("dsdxccxsddsxs"));
    }

    @Test
    public void checkIfPasswordIsMatching() {
        assertFalse(signUpActivity.isPasswordMatch("Ab1!azasb2#2121","Ab1!azasb2#2122"));
        assertTrue(signUpActivity.isPasswordMatch("Ab1!azasb2#2121","Ab1!azasb2#2121"));
    }
    @Test
    public void checkIfEncryptionWorks() throws Exception {
        String a = signUpActivity.encrypt("checking");
        assertEquals("checking",signUpActivity.decrypt(a));

    }

    @Test
    public void checkIfEmailValid(){
        assertTrue(signUpActivity.isEmailValid("mp@dal.ca"));
        assertFalse(signUpActivity.isEmailValid("mp.12dal.ca"));

    }


}