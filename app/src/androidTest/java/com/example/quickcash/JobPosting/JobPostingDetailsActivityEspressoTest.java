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
import com.example.quickcash.TaskList.TaskListActivity;

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
        onView(withId(R.id.txtJPDType)).check(matches(withText("Something")));
    }
}
