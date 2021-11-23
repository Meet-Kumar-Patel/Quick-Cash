package com.example.quickcash.JobPosting;

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

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobPostingDetailsActivityJUnitTest {
    private static JobPostingDetailsActivity jobPostingDetailsActivity;
    static JobPosting jobPosting;
    @BeforeClass
    public static void setup() throws Exception {
        jobPosting =  createJP();
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

    @Test
    public void CheckIfCovertJPTypeReturnCorrectString() {
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(0), "Repairing Computer");
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(1), "Mowing The Lawn");
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(2), "Design Website");
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(3), "Walking A Dog");
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(4), "Hourly Babysitting");
        Assert.assertEquals(jobPostingDetailsActivity.convertJPType(5), "Picking Up Grocery");
    }

}
