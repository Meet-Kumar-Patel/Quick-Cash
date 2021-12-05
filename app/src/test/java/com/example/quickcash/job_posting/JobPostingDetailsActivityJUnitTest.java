package com.example.quickcash.job_posting;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * This class is responsible for testing the job posting details activity class
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class JobPostingDetailsActivityJUnitTest {
    private static JobPostingDetailsActivity jobPostingDetailsActivity;
    static JobPosting jobPosting;

    @BeforeClass
    public static void setup() throws Exception {
        jobPosting = createJP();
        jobPostingDetailsActivity = Mockito.mock(JobPostingDetailsActivity.class);
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Intent intent = Mockito.mock(Intent.class);
        Mockito.when(jobPostingDetailsActivity.convertJPType(0)).thenReturn("Repairing Computer");
        Mockito.when(jobPostingDetailsActivity.convertJPType(1)).thenReturn("Mowing The Lawn");
        Mockito.when(jobPostingDetailsActivity.convertJPType(2)).thenReturn("Design Website");
        Mockito.when(jobPostingDetailsActivity.convertJPType(3)).thenReturn("Walking A Dog");
        Mockito.when(jobPostingDetailsActivity.convertJPType(4)).thenReturn("Hourly Babysitting");
        Mockito.when(jobPostingDetailsActivity.convertJPType(5)).thenReturn("Picking Up Grocery");
    }

    /**
     * to create a job posting for the class.
     * @return new job posting
     */
    private static JobPosting createJP() {
        String jobTitle = "Repair VivoBook";
        int jobType = 0;
        int duration = 2;
        String location = "Cole Harbour";
        int wage = 20;
        String createdBy = "employer@test.com";
        boolean isTaskComplete = false;
        ArrayList<String> lstAppliedBy = new ArrayList();
        lstAppliedBy.add("employee@test.com");
        String accepted = "";
        String createdByName = "Employer Test";
        return new JobPosting(jobTitle, jobType, duration, location, wage, createdBy, createdByName);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /**
     * To check if the job type is returning the correct string or not.
     */
    @Test
    public void CheckIfCovertJPTypeReturnCorrectString() {
        Assert.assertEquals("Repairing Computer", jobPostingDetailsActivity.convertJPType(0));
        Assert.assertEquals("Mowing The Lawn", jobPostingDetailsActivity.convertJPType(1));
        Assert.assertEquals("Design Website", jobPostingDetailsActivity.convertJPType(2));
        Assert.assertEquals("Walking A Dog", jobPostingDetailsActivity.convertJPType(3));
        Assert.assertEquals("Hourly Babysitting", jobPostingDetailsActivity.convertJPType(4));
        Assert.assertEquals("Picking Up Grocery", jobPostingDetailsActivity.convertJPType(5));
    }


}
