package com.example.quickcash.JobPosting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

import java.util.ArrayList;

public class JobPostingDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);

        DAOJobPosting daoJobPosting = new DAOJobPosting();
        ArrayList <String> lstIds = new ArrayList<>();
        lstIds.add("employee@test.com");
        lstIds.add("employee2@test.com");

        JobPosting jobPosting = new JobPosting("Repairing Asus", 0, 12,"Halifax",25.55,"employer@test.com");
        jobPosting.setAccepted("employee@test.com");
        jobPosting.setLstAppliedBy(lstIds);
        daoJobPosting.add(jobPosting);

        // Job Posting Status: Apply now (Btn) (add name to lstApplied), Pending (txt), Accepted or Rejected
        // Apply Now should change to Applied (disabled) when the user has applied or hide.


    }



}
