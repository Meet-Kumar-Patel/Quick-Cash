package com.example.quickcash.task_list;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.job_posting.JobPosting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This class is testing the adapter class.
 */
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

    /**
     * To test if the job posting array list is retrieved or not.
     */
    @Test
    public void testGetJobPostingArrayList() {
        assertEquals(jobPostingArrayList, recyclerAdapter.getJobPostingArrayList());
    }

    /**
     * To test if the job posting is retrieved or not.
     */
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
