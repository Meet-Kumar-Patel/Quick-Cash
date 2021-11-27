package com.example.quickcash.UserManagement.TaskList;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.TaskList.RecyclerAdapter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class TaskListRecyclerAdapterJUnitTest {

    static RecyclerAdapter recyclerAdapter;
    static ArrayList<JobPosting> jobPostingArrayList;

    @BeforeClass
    public static void setup() {
        jobPostingArrayList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setJobPostingArrayList(jobPostingArrayList);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testGetJobPostingArrayList() {
        assertEquals(jobPostingArrayList, recyclerAdapter.getJobPostingArrayList());
    }

    @Test
    public void testGetJobPosting() {
        JobPosting jobPosting = new JobPosting("Test", 0, 0, "Halifax", 0, "test@test.com", "test");
        jobPostingArrayList.add(jobPosting);
        recyclerAdapter.setJobPostingArrayList(jobPostingArrayList);
        String jobTitle = "Test";
        ArrayList<JobPosting> jpArr = recyclerAdapter.getJobPostingArrayList();
        assertEquals(jobTitle, jpArr.get(0).getJobTitle());
    }

}
