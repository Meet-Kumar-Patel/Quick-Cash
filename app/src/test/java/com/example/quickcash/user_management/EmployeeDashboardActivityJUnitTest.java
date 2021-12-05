package com.example.quickcash.user_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.job_posting.JobPosting;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeDashboardActivityJUnitTest {

    static JobPosting mockedPosting = null;

    @BeforeClass
    public static void setup() {
        mockedPosting = Mockito.mock(JobPosting.class);
        Mockito.when(mockedPosting.getAccepted()).thenReturn("");
        Mockito.when(mockedPosting.getJobTitle()).thenReturn("");
    }

    /*@Test
    public void testRetrieveDataFromFirebase()
    {
        EmployeeDashboardActivity employeeDashboardActivity = new EmployeeDashboardActivity();
        employeeDashboardActivity.retrieveDataFromFirebase("");
        assertEquals("", employeeDashboardActivity.getStatusMessage());
    }*/
}
