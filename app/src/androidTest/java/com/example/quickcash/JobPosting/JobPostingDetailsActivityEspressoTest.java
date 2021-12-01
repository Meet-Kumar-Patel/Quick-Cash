package com.example.quickcash.JobPosting;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.R;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class JobPostingDetailsActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<JobPostingDetailsActivity> myRule = new ActivityScenarioRule<>(JobPostingDetailsActivity.class);

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
        onView(withId(R.id.txtJobTitle)).check(matches(withText("Job Title")));
    }

    @Test
    public void checkIfTypeIsVisible() {
        onView(withId(R.id.txtJPDTypeValue)).check(matches(withText("Job Type")));
    }

    @Test
    public void checkIfEmployerIsVisible() {
        onView(withId(R.id.txtJDPCreatedByValue)).check(matches(withText("Employer")));
    }

    @Test
    public void checkIfDurationIsVisible() {
        onView(withId(R.id.txtJPDDurationValue)).check(matches(withText("Duration")));
    }

    @Test
    public void checkIfWageIsVisible() {
        onView(withId(R.id.txtJPDWageValue)).check(matches(withText("Wage")));
    }

    @Test
    public void checkIfLocationIsVisible() {
        onView(withId(R.id.txtJDPLocationValue)).check(matches(withText("Location")));
    }

    @Test
    public void checkIfStatusIsVisible() {
        onView(withId(R.id.txtJPDStatusValue)).check(matches(withText("Press 'Apply Now' to register")));
    }

    @Test
    public void checkIfSearchMoreIsVisible() {
        onView(withId(R.id.btnJPDReturnToSearch)).check(matches(withText("SEARCH MORE")));
    }

}
