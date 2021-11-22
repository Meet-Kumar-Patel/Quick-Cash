package com.example.quickcash.JobPosting;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.app.Instrumentation.ActivityResult;

import androidx.core.app.ComponentActivity;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class JobPostingActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<JobPostingActivity> myRule = new ActivityScenarioRule<>(JobPostingActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void checkIfCreateJPTitleISVisible() {

        onView(withId(R.id.txtCreateJPTitle)).check(matches(withText("Create Job Posting")));
    }

    @Test
    public void checkIfCreateJPBtnIsVisible() {
        onView(withId(R.id.btnCreateJP)).check(matches(withText("CREATE JOB POSTING")));
    }

    @Test
    public void checkIfReturnToHomePageIsVisible() {
        onView(withId(R.id.btnJPHomePage)).check(matches(withText("RETURN TO HOME PAGE")));
    }

    @Test
    public void checkIfInputFieldsAreVisible() {

        onView(withId(R.id.etJPTitle)).check(matches(withText("")));
        onView(withId(R.id.etDuration)).check(matches(withText("")));
        onView(withId(R.id.etLocation)).check(matches(withText("")));
        onView(withId(R.id.etWage)).check(matches(withText("")));
        onView(withId(R.id.etErrorJP)).check(matches(withText("")));
    }

    @Test
    public void checkIfSpinnerIsFunctional() {
        onView(withId(R.id.etJPType)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.etJPType)).check(matches(withSpinnerText(containsString("Mowing The Lawn"))));
    }


    @Test
    public void checkIfJobTitleIsEmpty() {
        onView(withId(R.id.etJPTitle)).perform(closeSoftKeyboard());
        onView(withId(R.id.btnCreateJP)).perform(click());
        onView(withId(R.id.etErrorJP)).check(matches(withText("Job title is required.")));
    }


    @Test
    public void checkIfDurationIsEmpty() {
        onView(withId(R.id.etJPTitle)).perform(typeText("Evening Pet Care")).perform(closeSoftKeyboard());
        onView(withId(R.id.etJPType)).perform(click());
        onData(anything()).atPosition(3).perform(click());

        onView(withId(R.id.btnCreateJP)).perform(click());
        onView(withId(R.id.etErrorJP)).check(matches(withText("Duration 1 day or longer is required.")));
    }

    @Test
    public void checkIfWageIsEmpty() {
        onView(withId(R.id.etJPTitle)).perform(typeText("Evening Pet Care")).perform(closeSoftKeyboard());
        onView(withId(R.id.etJPType)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.etDuration)).perform(typeText("2")).perform(closeSoftKeyboard());
        onView(withId(R.id.etLocation)).perform(clearText()).perform(closeSoftKeyboard());
        onView(withId(R.id.etLocation)).perform(typeText("Halifax")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnCreateJP)).perform(click());
        onView(withId(R.id.etErrorJP)).check(matches(withText("Wage less than $15 are not accepted.")));
    }

    @Test
    public void checkIfLocation() {
        onView(withId(R.id.etJPTitle)).perform(typeText("Evening Pet Care")).perform(closeSoftKeyboard());
        onView(withId(R.id.etJPType)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.etDuration)).perform(typeText("2")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnCreateJP)).perform(click());
        onView(withId(R.id.etErrorJP)).check(matches(withText("Please Enter The Location.")));
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

}
