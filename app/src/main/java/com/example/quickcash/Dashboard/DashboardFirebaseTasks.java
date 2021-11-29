package com.example.quickcash.Dashboard;

import androidx.annotation.NonNull;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.TaskList.TaskListRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    public void getDashboardJobs(EmployeeDashboardActivity employeeDashboardActivity, String email) {
        DatabaseReference jobPostingReference = db.getReference("JobPosting");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if(jp.getLstAppliedBy() != null && jp.getLstAppliedBy().contains(email)) {
                        employeeDashboardActivity.addJobToArray(jp);
                    }
                }
                employeeDashboardActivity.setAdapter(new TaskListRecyclerAdapter(employeeDashboardActivity, employeeDashboardActivity.getJobsAppliedForArray()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    public void getDashboardJobs(EmployerDashboardActivity employerDashboardActivity, String email) {
        DatabaseReference jobPostingReference = db.getReference("JobPosting");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if(jp.getCreatedBy().equals(email)) {
                        employerDashboardActivity.addJobToArray(jp);
                    }
                }
                employerDashboardActivity.setAdapter(new TaskListRecyclerAdapter(employerDashboardActivity, employerDashboardActivity.getJobsCreatedArray()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }
}
