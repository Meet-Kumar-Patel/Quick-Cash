package com.example.quickcash.home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.R;
import com.example.quickcash.task_list.TaskListActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//Citations: The format of the tests below are taken from the tests in Assignment 2

/**
 * This class is testing the employee home package
 */
@RunWith(AndroidJUnit4.class)
public class EmployeeHomeActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<EmployeeHomeActivity> loginRule = new ActivityScenarioRule<EmployeeHomeActivity>(EmployeeHomeActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    // to check if the page is created or not.
    @Test
    public void checkIfPageCreated() {
        onView(withId(R.id.etEmployeeMessage)).check(matches(withText("Welcome to Employee Home")));
    }

    // to check if the user is moved to TaskList activity when clicked a search task button or not.
    @Test
    public void checkIfMoved2SearchTasksPage() {
        onView(withId(R.id.btnCreateTasksEmployerPage)).perform(click());
        intended(hasComponent(TaskListActivity.class.getName()));
    }

    // to check if the user is moved to EmployeeHome activity when clicked a dashboard button or not.
    @Test
    public void checkIfMoved2DashboardPage() {
        onView(withId(R.id.btnDashboardEmployerPage)).perform(click());
        intended(hasComponent(EmployeeHomeActivity.class.getName()));
    }

    // to check if the user is moved to EmployeeHomeActivity activity when clicked a search by pref button or not.
    @Test
    public void checkIfMoved2SearchByPrefPage() {
        onView(withId(R.id.btnSearchByPrefEmployeePage)).perform(click());
        intended(hasComponent(EmployeeHomeActivity.class.getName()));
    }

    // check to see if the log out is working or not.
    @Test
    public void checkIfLogOut() {
        onView(withId(R.id.btnLogOutEmployee)).perform(click());
        intended(hasComponent(EmployeeHomeActivity.class.getName()));
    }
}
