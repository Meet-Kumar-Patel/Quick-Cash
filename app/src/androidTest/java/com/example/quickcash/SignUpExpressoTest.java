package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import static androidx.test.espresso.action.ViewActions.click;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.quickcash.UserManegement.SignUpActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpExpressoTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> myRule = new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    @Test
    public void checkIfRegistrationPageIsVisible() {
        onView(withId(R.id.txtFirstName)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtEmail)).check(matches(withText("")));
        onView(withId(R.id.txtUserEnteredPassword)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtPhone)).check(matches(withText("")));
    }

    @Test
    public void checkIfFirstNameIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter Your First Name")));
    }


    @Test
    public void checkIfLastNameIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter Your Last Name")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter your email address")));
    }

    @Test
    public void checkIfPhoneNumberIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please enter your phone number")));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter your password")));
    }

    @Test
    public void checkIfConfirmPasswordIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter to confirm your password")));
    }

    @Test
    public void checkIfRadioButtonIsSelected() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Select one of the two given options")));
    }


    @AfterClass
    public static void tearDown() {
        System.gc();
    }


}