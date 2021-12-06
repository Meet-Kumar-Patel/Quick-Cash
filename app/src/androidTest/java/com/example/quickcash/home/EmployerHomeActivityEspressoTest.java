package com.example.quickcash.home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.R;
import com.example.quickcash.WelcomePage;
import com.example.quickcash.dashboard.EmployerDashboardActivity;
import com.example.quickcash.user_management.MapsActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//Citations: The format of the tests below are taken from the tests in Assignment 2
/**
 * This class is testing the  employer home package
 */
@RunWith(AndroidJUnit4.class)
public class EmployerHomeActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<EmployerHomeActivity> loginRule = new ActivityScenarioRule<EmployerHomeActivity>(EmployerHomeActivity.class);
    //public IntentsTestRule<EmployerHomeActivity> myIntentRule = new IntentsTestRule<>(EmployeeHomeActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    // to check if the page is created or not.
    @Test
    public void checkIfPageCreated() {
        onView(withId(R.id.etEmployeeMessage)).check(matches(withText("Welcome to Employer Home")));

    }

    // to check if the user is able to move to EmployerHomeActivity or not when click create task.
    @Test
    public void checkIfMoved2CreateTasksPage() {
        onView(withId(R.id.btnCreateTasksEmployerPage)).perform(click());
        intended(hasComponent(MapsActivity.class.getName()));
    }

    // to check if the user is able to move to EmployerHomeActivity or not when clicked dashboard.
    @Test
    public void checkIfMoved2DashboardPage() {
        onView(withId(R.id.btnDashboardEmployerPage)).perform(click());
        intended(hasComponent(EmployerDashboardActivity.class.getName()));
    }

    // to check if the user is able to logout or not..
    @Test
    public void checkIfLogOut() {
        onView(withId(R.id.btnLogOutEmployer)).perform(click());
        intended(hasComponent(WelcomePage.class.getName()));
    }

}



