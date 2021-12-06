package com.example.quickcash.task_list;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.job_posting.JobTypeStringGetter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This class is responsible for testing the Job Filter JUnit Test.
 */
public class JobFilterJUnitTest {

    static ArrayList<JobPosting> jobPostingArrayList;
    static TaskListFilterFactory taskListFilterFactory;
    TaskListRecyclerAdapter taskListRecyclerAdapter = new TaskListRecyclerAdapter();
    static JobPosting jobPosting1 = new JobPosting("Test1", 0, 0, "Halifax", 0, "test@test.com", "test");
    static JobPosting jobPosting2 = new JobPosting("Test2", 1, 0, "Halifax", 0, "test@test.com", "test");

    @BeforeClass
    public static void setup() {
        taskListFilterFactory = new TaskListFilterFactory();
        jobPostingArrayList = new ArrayList<>();
        jobPostingArrayList.add(jobPosting1);
        jobPostingArrayList.add(jobPosting2);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /**
     * This test is to check if the job name is filtered or not.
     */
    @Test
    public void testFilterByJobName() {
        ArrayList<JobPosting> filteredArrList =
                (ArrayList<JobPosting>) taskListFilterFactory.
                        filterJobPostingList(jobPostingArrayList,"Test2");
        assertEquals(jobPosting2,filteredArrList.get(0));
    }

    /**
     * This test is to check if the job type is filtered or not.
     */
    @Test
    public void testFilterByJobType() {
        ArrayList<JobPosting> filteredArrList =
                (ArrayList<JobPosting>) taskListFilterFactory.
                        filterJobPostingList(jobPostingArrayList, JobTypeStringGetter.getJobType(1));
        assertEquals(jobPosting2,filteredArrList.get(0));
    }
}
