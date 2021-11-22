package com.example.quickcash.UserManagement.JobPosting;

import android.content.Intent;
import androidx.test.rule.ActivityTestRule;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobPostingDetailsActivityJUnitTest {
    private static JobPostingActivity jobPostingActivity;

    @Test
    public void testName() {

    }

    @BeforeClass
    public static void setup() {
        jobPostingActivity = Mockito.mock(JobPostingActivity.class);
        Intent i = new Intent();
        i.putExtra(JobPostingActivity.EXTRA_MESSAGE, "Halifax");
        Mockito.when(i.getStringExtra(JobPostingActivity.EXTRA_MESSAGE)).thenReturn("Halifax");
    }

    // Verify that the Job posting details are being correctly displayed.
}
