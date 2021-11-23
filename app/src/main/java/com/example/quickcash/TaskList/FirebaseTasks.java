package com.example.quickcash.TaskList;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import androidx.annotation.NonNull;

import com.example.quickcash.JobPosting.JobPosting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    public void getJobPostingsFromFirebase(TaskListActivity taskListActivity, String city) {
        DatabaseReference jobPostingReference = db.getReference("JobPosting");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if(jp.getAccepted().equals("")) {
                        if (city != null) {
                            if (jp != null && jp.getLocation().equals(city)) {
                                taskListActivity.addJobPostingToArray(jp);
                            }
                        } else {
                            taskListActivity.addJobPostingToArray(jp);
                        }
                    }
                }
                taskListActivity.setAdapter(new RecyclerAdapter(taskListActivity, taskListActivity.getJobPostingArrayList()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

}
