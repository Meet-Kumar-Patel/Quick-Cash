package com.example.quickcash.UserManagement;

import org.junit.*;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.R;
import com.example.quickcash.UserManagement.LogInActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<LogInActivity> loginRule = new ActivityScenarioRule<LogInActivity>(LogInActivity.class);
    static LogInActivity loginActivity;
    // Check if the email exist in the firebase
    // if the email E in the firebase => check if the password is valid
    @Test
    public void checkIfEmailIsValid() {
        // Fill the required
        onView(withId(R.id.etEmail)).perform(typeText("email@test.com"));
        onView(withId(R.id.etPassword)).perform((typeText("12345")));

        // Click the registration Button
        onView(withId(R.id.btnLogin)).perform(click());

        // Compare the result
        onView(withId(R.id.etError)).check(matches(withText("Email Found!")));
    }

    // if the email does not E in the firebase => notify "Username/password invalid".
    @Test
    public void checkIfEmailIsInvalid() {
        // Fill the required
        onView(withId(R.id.etEmail)).perform(typeText("wrong@test.com"));
        onView(withId(R.id.etPassword)).perform((typeText("12345")));

        // Click the registration Button
        onView(withId(R.id.btnLogin)).perform(click());

        // Compare the result
        onView(withId(R.id.etError)).check(matches(withText("Email Not Found.")));

    }

}
