package com.example.quickcash.TaskList;

import androidx.annotation.NonNull;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.UserManagement.Preference;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskListFirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
    public void getJobPostingsFromFirebase(TaskListActivity taskListActivity, String city) {
        DatabaseReference jobPostingReference = db.getReference(JobPosting.class.getSimpleName());
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    addJobPosting(jp, city, taskListActivity);
                }
                taskListActivity.setAdapter(new TaskListRecyclerAdapter(taskListActivity, taskListActivity.getJobPostingArrayList()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    private void addJobPosting(JobPosting jp, String city, TaskListActivity taskListActivity) {
        if(jp.getAccepted().equals("")) {
            if (city != null) {
                if (jp.getLocation().equals(city)) {
                    taskListActivity.addJobPostingToArray(jp);
                }
            } else {
                taskListActivity.addJobPostingToArray(jp);
            }
        }
    }

    public void getUserPreferenceFromFirebase(TaskListActivity taskListActivity, String email){
        DatabaseReference preferenceReference = db.getReference("Preference");
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    Preference pref = adSnapshot.getValue(Preference.class);
                    retrievePreference(pref, email, taskListActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrievePreference(Preference pref, String email, TaskListActivity taskListActivity) {
        if(pref.getEmployeeEmail() != null && pref.getEmployeeEmail().equals(email)) {
            int taskPrefType = pref.getJobType();
            String taskPref = JobTypeStringGetter.getJobType(taskPrefType);
            taskListActivity.setSearchQuery(taskPref);
        }
    }

}
