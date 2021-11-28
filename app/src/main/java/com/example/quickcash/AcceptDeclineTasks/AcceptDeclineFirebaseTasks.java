package com.example.quickcash.AcceptDeclineTasks;

import androidx.annotation.NonNull;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.UserManagement.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineFirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    public void getJobPostingsFromFirebase(AcceptDeclineTasks acceptDeclineTasks, String createdByEmail) {
        DatabaseReference jobPostingReference = db.getReference("JobPosting");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if(jp.getCreatedBy().equals(createdByEmail)) {
                        acceptDeclineTasks.addJobPostingToHashMap(adSnapshot.getKey(), jp);
                    }

                }
                getUsersFromFirebase(acceptDeclineTasks, acceptDeclineTasks.getJobPostingHashMap());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    public void getUsersFromFirebase(AcceptDeclineTasks acceptDeclineTasks, HashMap<String, JobPosting> hashMap){
        DatabaseReference userReference = db.getReference("User");
        ArrayList<String> emails = acceptDeclineTasks.getAppliedUserEmailsFromHashMap(hashMap);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot adSnapshot : snapshot.getChildren()) {
                    User user = adSnapshot.getValue(User.class);
                    for(String email : emails) {
                        if(email.equals(user.getEmail())){
                            acceptDeclineTasks.addUserToArray(user);
                        }
                    }
                }
                acceptDeclineTasks.setAdapter(new AcceptDeclineRecyclerAdapter(acceptDeclineTasks, acceptDeclineTasks.getUserArrayList()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
