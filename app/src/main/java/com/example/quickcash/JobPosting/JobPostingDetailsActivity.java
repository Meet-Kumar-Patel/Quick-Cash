package com.example.quickcash.JobPosting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JobPostingDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);

        DAOJobPosting daoJobPosting = new DAOJobPosting();
        ArrayList <Integer> lstIds = new ArrayList<>();
        lstIds.add(1234);
        lstIds.add(745437);

        JobPosting jobPosting = new JobPosting("JobTitle", 12, "Halifax", 50,
                12345, false, lstIds, 1234);
        daoJobPosting.add(jobPosting);
    }



}
