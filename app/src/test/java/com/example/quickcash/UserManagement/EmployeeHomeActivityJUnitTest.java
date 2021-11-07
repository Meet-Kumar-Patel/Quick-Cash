package com.example.quickcash.UserManagement;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;

import org.junit.Test;
import org.mockito.Mockito;

public class EmployeeHomeActivityJUnitTest {

    @Test
    public void testRetrieveDataFromFirebase()
    {
        JobPosting mockedPosting = Mockito.mock(JobPosting.class);
        Mockito.when(mockedPosting.getAccepted()).thenReturn("");

        /*EmployeeHomeActivity employeeHomeActivity = new EmployeeHomeActivity();
        employeeHomeActivity.retrieveDataFromFirebase();*/
    }
}
