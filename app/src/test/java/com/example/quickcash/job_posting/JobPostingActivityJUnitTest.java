package com.example.quickcash.job_posting;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class is responsible for checking the Job posting package
 */
public class JobPostingActivityJUnitTest {
    static JobPostingActivity jobPostingActivityMock;
    JobPostingActivity jobPostingActivity;

    @BeforeClass
    public static void setup() {
        jobPostingActivityMock = Mockito.mock(JobPostingActivity.class);
        Mockito.when(jobPostingActivityMock.getJobTitle()).thenReturn("Repairing Computer");
        Mockito.when(jobPostingActivityMock.getDuration()).thenReturn(2);
        Mockito.when(jobPostingActivityMock.getLocation()).thenReturn("Halifax");
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /**
     * to check if the job title is returned or not.
     */
    @Test
    public void CheckIfGetJobTitleReturns() {
        Assert.assertEquals("Repairing Computer", jobPostingActivityMock.getJobTitle());
    }

    /**
     * to check if the duration is returned or not.
     */
    @Test
    public void CheckIfGetDurationReturns() {
        Assert.assertEquals(2, jobPostingActivityMock.getDuration());
    }

    /**
     * to check if the location is returned or not.
     */
    @Test
    public void CheckIfGetLocationReturns() {
        Assert.assertEquals("Halifax", jobPostingActivityMock.getLocation());
    }

}
