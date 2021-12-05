package com.example.quickcash.Dashboard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.TaskList.TaskListRecyclerAdapter;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);

    /**
     * Gets from Firebase the jobs that the employee has applied to.
     * @param employeeDashboardActivity
     * @param email, employee email
     */
    public void getDashboardJobs(EmployeeDashboardActivity employeeDashboardActivity,
                                 String email) {
        DatabaseReference jobPostingReference = db.getReference(JobPosting.class.getSimpleName());
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if (jp.getLstAppliedBy() != null && jp.getLstAppliedBy().contains(email)) {
                        employeeDashboardActivity.addJobToArray(jp);
                    }
                }
                employeeDashboardActivity.setAdapter(new TaskListRecyclerAdapter(
                        employeeDashboardActivity,
                        employeeDashboardActivity.getJobsAppliedForArray()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    /**
     * Gets from Firebase the jobs created by the current employer
     * @param employerDashboardActivity
     * @param email, employer email
     */
    public void getDashboardJobs(EmployerDashboardActivity employerDashboardActivity, String email) {
        DatabaseReference jobPostingReference = db.getReference(JobPosting.class.getSimpleName());
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if (jp.getCreatedBy().equals(email)) {
                        employerDashboardActivity.addJobToArray(jp);
                    }
                }
                employerDashboardActivity.setAdapter(new TaskListRecyclerAdapter(employerDashboardActivity, employerDashboardActivity.getJobsCreatedArray()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }
}
