package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

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
    public void checkIfRegistrationPageIsVisible() {
        onView(withId(R.id.txtFirstName)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtEmail)).check(matches(withText("")));
        onView(withId(R.id.txtUserEnteredPassword)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtPhone)).check(matches(withText("")));
    }





    @AfterClass
    public static void tearDown() {
        System.gc();
    }


}