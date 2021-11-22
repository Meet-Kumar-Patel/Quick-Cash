package com.example.quickcash.UserManagement.JobPosting;

import com.example.quickcash.JobPosting.JobPosting;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobPostingDetailsActivityJUnitTest {
    static JobPosting mockedPosting = null;

    @BeforeClass
    public static void setup() {
        mockedPosting = Mockito.mock(JobPosting.class);
    }

    // Verify that the Job posting details are being correctly displayed.
}
