package com.example.quickcash.Home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.R;
import com.example.quickcash.UserManagement.LogInActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeHomeActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<EmployeeHomeActivity> loginRule = new ActivityScenarioRule<EmployeeHomeActivity>(EmployeeHomeActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }
    @Test
    public void checkIfPageCreated() {
        onView(withId(R.id.etEmail)).perform(typeText("email@test.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform((typeText("12345"))).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etEmployeeMessage)).check(matches(withText("Welcome to Employee Home")));
    }

}
