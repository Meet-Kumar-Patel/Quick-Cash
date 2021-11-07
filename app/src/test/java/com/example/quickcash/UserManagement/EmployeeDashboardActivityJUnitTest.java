package com.example.quickcash.UserManagement;

import static org.junit.Assert.assertTrue;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeHomeActivityJUnitTest {

    static JobPosting mockedPosting = null;

    @BeforeClass
    public static void setup() {
        mockedPosting = Mockito.mock(JobPosting.class);
        Mockito.when(mockedPosting.getAccepted()).thenReturn("");
        Mockito.when(mockedPosting.getJobTitle()).thenReturn("");
    }

    @Test
    public void testRetrieveDataFromFirebase()
    {
        EmployeeHomeActivity employeeHomeActivity = new EmployeeHomeActivity();
        employeeHomeActivity.retrieveDataFromFirebase("");
        assertTrue(employeeHomeActivity.getStatusMessage().equals(""));
    }
}
