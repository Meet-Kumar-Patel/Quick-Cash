package com.example.quickcash.TaskList;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.Looper;
import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class TaskListEspressoTest {

    TaskListActivity taskListActivity;
    RecyclerAdapter recyclerAdapter;
    SessionManager sessionManager = new SessionManager(getApplicationContext());
    ArrayList<JobPosting> jobPostingArrayList;
    JobPosting dummyJobPosting = new JobPosting("Test Job", 0, 20, "Halifax", 20, "logan@test.com", "Logan");

    @Rule
    public ActivityScenarioRule<TaskListActivity> myRule = new ActivityScenarioRule<>(TaskListActivity.class);

    @Before
    public void initTaskListActivity() {
        taskListActivity = new TaskListActivity();
    }

    @Before
    public void initRecyclerAdapter() {
        jobPostingArrayList = new ArrayList<>();
        jobPostingArrayList.add(dummyJobPosting);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), jobPostingArrayList);
        taskListActivity.setAdapter(recyclerAdapter);
    }

    @BeforeClass
    public static void setup() {
        Intents.init();
        Looper.prepare();
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

    @Test
    public void checkIfPageIsVisible() {
        onView(ViewMatchers.withId(R.id.recyclerview));
        onView(ViewMatchers.withId(R.id.resetbutton));
        onView(ViewMatchers.withId(R.id.jobsearch));
    }

    @Test
    public void checkForRecyclerViewElement() {
        onView(ViewMatchers.withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Test Job"))));
    }

}
