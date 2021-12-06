package com.example.quickcash.task_list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.R;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class is responsible for testing the task_list package
 */
public class TaskListEspressoTest {

    @Rule
    public ActivityScenarioRule<TaskListActivity> myRule = new ActivityScenarioRule<>(TaskListActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    // to check if the page is visible or not.
    @Test
    public void checkIfPageIsVisible() {
        onView(ViewMatchers.withId(R.id.recyclerview));
        onView(ViewMatchers.withId(R.id.resetbutton));
        onView(ViewMatchers.withId(R.id.jobsearch));
    }

}
