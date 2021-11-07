package com.example.quickcash.JobPosting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;

import java.util.ArrayList;

public class JobPostingDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);

        // Ensure that the user is logged in
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //access the intents & show the welcome message
        Intent intent = getIntent();
        //Received location from map and show to the user
        String jobID = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE).toString();

        // Job Posting Status: Apply now (Btn) (add name to lstApplied), Pending (txt), Accepted or Rejected
        // Apply Now should change to Applied (disabled) when the user has applied or hide.

    }



}
