package com.example.quickcash.UserManagement;

import org.junit.*;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

import android.widget.ScrollView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.LogInActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<LogInActivity> loginRule = new ActivityScenarioRule<LogInActivity>(LogInActivity.class);
    public IntentsTestRule<LogInActivity> myIntentRule = new IntentsTestRule<>(LogInActivity.class);
    static LogInActivity loginActivity;

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    //Checks if proper message appears while waiting for information to be retrieved from the firebase.
    @Test
    public void checkIfFirebaseIsConnecting() {
        onView(withId(R.id.etEmail)).perform(typeText("email@test.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform((typeText("12345"))).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etError)).check(matches(withText("Verifying credentials")));
    }

    //Checks if the correct status message appears when improper email is entered.
    @Test
    public void checkIfProperEmail() {
        onView(withId(R.id.etEmail)).perform(typeText("email.test.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform((typeText("12345"))).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etError)).check(matches(withText("Improper Email Address")));
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

}
