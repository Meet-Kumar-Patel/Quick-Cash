package com.example.quickcash.TaskList;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.JobPosting.JobPosting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class TaskListRecyclerAdapterJUnitTest {

    static TaskListRecyclerAdapter recyclerAdapter;
    static ArrayList<JobPosting> jobPostingArrayList;

    @BeforeClass
    public static void setup() {
        jobPostingArrayList = new ArrayList<>();
        recyclerAdapter = new TaskListRecyclerAdapter();
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
        ArrayList<JobPosting> jpArr = (ArrayList<JobPosting>) recyclerAdapter.getJobPostingArrayList();
        assertEquals(jobTitle, jpArr.get(0).getJobTitle());
    }

}