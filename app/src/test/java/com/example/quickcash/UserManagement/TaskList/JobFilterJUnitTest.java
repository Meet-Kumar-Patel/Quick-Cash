package com.example.quickcash.UserManagement.TaskList;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.TaskList.JobFilter;
import com.example.quickcash.TaskList.JobTypeStringGetter;
import com.example.quickcash.TaskList.RecyclerAdapter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class JobFilterJUnitTest {

    static ArrayList<JobPosting> jobPostingArrayList;
    static JobPosting jobPosting1 = new JobPosting("Test1", 0, 0, "Halifax", 0, "test@test.com", "test");
    static JobPosting jobPosting2 = new JobPosting("Test2", 1, 0, "Halifax", 0, "test@test.com", "test");

    @BeforeClass
    public static void setup() {
        jobPostingArrayList = new ArrayList<>();
        jobPostingArrayList.add(jobPosting1);
        jobPostingArrayList.add(jobPosting2);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testFilterByJobName() {
        ArrayList<JobPosting> filteredArrList = JobFilter.filter(jobPostingArrayList,"Test2");
        assertEquals(jobPosting2,filteredArrList.get(0));
    }

    @Test
    public void testFilterByJobType() {
        ArrayList<JobPosting> filteredArrList = JobFilter.filter(jobPostingArrayList, JobTypeStringGetter.getJobType(1));
        assertEquals(jobPosting2,filteredArrList.get(0));
    }
}
