package com.example.quickcash.JobPosting;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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
    @Test
    public void CheckIfGetJobTitleReturns() {
        Assert.assertEquals(jobPostingActivityMock.getJobTitle(), "Repairing Computer");
    }

    @Test
    public void CheckIfGetDurationReturns() {
        Assert.assertEquals(jobPostingActivityMock.getDuration(), 2);
    }

    @Test
    public void CheckIfGetLocationReturns() {
        Assert.assertEquals(jobPostingActivityMock.getLocation(), "Halifax");
    }

}
