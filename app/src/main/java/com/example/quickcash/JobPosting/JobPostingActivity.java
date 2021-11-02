package com.example.quickcash.JobPosting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

import java.util.ArrayList;


public class JobPostingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        DAOJobPosting daoJobPosting = new DAOJobPosting();
        ArrayList<String> lstIds = new ArrayList<>();
        lstIds.add("employee@test.com");
        lstIds.add("employee2@test.com");

        JobPosting jobPosting = new JobPosting("Take care Children", 4, 2,"Dartmouth",79.55,"employer@test.com");
        jobPosting.setAccepted("employee2@test.com");
        jobPosting.setLstAppliedBy(lstIds);
        daoJobPosting.add(jobPosting);
    }
}
