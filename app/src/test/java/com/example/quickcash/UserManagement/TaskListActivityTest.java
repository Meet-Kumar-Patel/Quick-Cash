package com.example.quickcash.UserManagement;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import android.widget.Filter;

import com.example.quickcash.TaskList.RecyclerAdapter;
import com.example.quickcash.TaskList.TaskListActivity;
import com.example.quickcash.JobPosting.JobPosting;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskListActivityTest {

    static TaskListActivity taskListActivity;


    @BeforeClass
    public static void setup() {
        taskListActivity = mock(TaskListActivity.class);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void taskListCreation() {
        JobPosting dummyJobPosting = new JobPosting("Test Job", 0, 20, "Halifax", 20, "logan@test.com", "Logan");
        taskListActivity.addJobPostingToArray(dummyJobPosting);
        ArrayList<JobPosting> taskArrList = taskListActivity.getJobPostingArrayList();
        assertEquals("Test Job", taskArrList.get(0).getJobTitle());
    }

}
