package com.example.quickcash.JobPosting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.core.app.ComponentActivity;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.MainActivity;
import com.example.quickcash.R;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class JobPostingActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<JobPostingActivity> myRule = new ActivityScenarioRule<>(JobPostingActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);

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
        onView(withId(R.id.etJPTitle)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnCreateJP)).perform(click());
        onView(withId(R.id.etErrorJP)).check(matches(withText("Job title is required.")));
    }
//
//    /*** UAT-III**/
//    @Test
//    public void checkIfBannerIDIsValid() {
//        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
//        onView(withId(R.id.bannerID)).perform(typeText("B00123456"));
//        onView(withId(R.id.registerButton)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
//    }
//
//    /*** UAT-III**/
//    @Test
//    public void checkIfBannerIDIsInvalid() {
//        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
//        onView(withId(R.id.bannerID)).perform(typeText("4512*bn!"));
//        onView(withId(R.id.registerButton)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_BANNER_ID)));
//    }
//
//    /*** UAT-IV**/
//    @Test
//    public void checkIfEmailIsValid() {
//        onView(withId(R.id.bannerID)).perform(typeText("B00123456"));
//        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
//        onView(withId(R.id.registerButton)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
//    }
//
//    /*** UAT-IV**/
//    @Test
//    public void checkIfEmailIsInvalid() {
//        onView(withId(R.id.bannerID)).perform(typeText("B00123896"));
//        onView(withId(R.id.emailAddress)).perform(typeText("abc123.dal.ca"));
//        onView(withId(R.id.registerButton)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_EMAIL_ADDRESS)));
//    }
//
//    /*** UAT-IV**/
//    @Test
//    public void checkIfEmailIsNotDALEmail() {
//        onView(withId(R.id.bannerID)).perform(typeText("B00123896"));
//        onView(withId(R.id.emailAddress)).perform(typeText("abc123@usask.ca"));
//        onView(withId(R.id.registerButton)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_DAL_EMAIL)));
//    }
//
//    /***UAT-V**/
//    @Test
//    public void checkIfMoved2WelcomePage() {
//        onView(withId(R.id.bannerID)).perform(typeText("B00456236"));
//        onView(withId(R.id.emailAddress)).perform(typeText("abc123@dal.ca"));
//        onView(withId(R.id.registerButton)).perform(click());
//        intended(hasComponent(WelcomeActivity.class.getName()));
//    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

}
