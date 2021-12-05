package com.example.quickcash.task_list;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.job_posting.JobTypeStringGetter;
import com.example.quickcash.user_management.Preference;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskListFirebaseTasks {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);

    public void getJobPostingsFromFirebase(TaskListActivity taskListActivity, String city) {
        DatabaseReference jobPostingReference =
                firebaseDatabase.getReference(JobPosting.class.getSimpleName());
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    addJobPosting(jp, city, taskListActivity);
                }
                TaskListRecyclerAdapter taskListRecyclerAdapter = new TaskListRecyclerAdapter
                        (taskListActivity, taskListActivity.getJobPostingList());
                taskListActivity.setAdapter(taskListRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(Constants.FIREBASE_ERROR, Constants.FIREBASE_ERROR + error.getCode());
            }
        });
    }

    private void addJobPosting(JobPosting jp, String city, TaskListActivity taskListActivity) {
        if (jp.getAccepted().equals("")) {
            if (city != null) {
                if (jp.getLocation().equals(city)) {
                    taskListActivity.addJobPostingToArray(jp);
                }
            } else {
                taskListActivity.addJobPostingToArray(jp);
            }
        }
    }

    public void getUserPreferenceFromFirebase(TaskListActivity taskListActivity, String email) {
        DatabaseReference preferenceReference =
                firebaseDatabase.getReference(Preference.class.getSimpleName());
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    Preference preference = adSnapshot.getValue(Preference.class);
                    retrievePreference(preference, email, taskListActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(Constants.FIREBASE_ERROR, Constants.FIREBASE_ERROR + error.getCode());
            }
        });
    }

    private void retrievePreference(Preference pref, String email,
                                    TaskListActivity taskListActivity) {
        if (pref.getEmployeeEmail() != null && pref.getEmployeeEmail().equals(email)) {
            int taskPrefType = pref.getJobType();
            String taskPref = JobTypeStringGetter.getJobType(taskPrefType);
            taskListActivity.setSearchQuery(taskPref);
        }
    }
}
