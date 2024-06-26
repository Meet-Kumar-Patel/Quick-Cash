package com.example.quickcash.accept_decline_tasks;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.user_management.User;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AcceptDeclineFirebaseTasks {

    FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);

    /**
     * Gets the job postings created by the current logged user and it passes the list to
     * getUsersFromFirebase()
     * @param acceptDeclineTasks
     * @param createdByEmail, email of the current logged user.
     */
    public void getJobPostingsFromFirebase(AcceptDeclineTasks acceptDeclineTasks,
                                           String createdByEmail) {
        DatabaseReference jobPostingReference = db.getReference(JobPosting.class.getSimpleName());
        jobPostingReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    if (jp.getCreatedBy().equals(createdByEmail)) {
                        acceptDeclineTasks.addJobPostingToHashMap(adSnapshot.getKey(), jp);
                    }

                }
                getUsersFromFirebase(acceptDeclineTasks, acceptDeclineTasks.getJobPostingHashMap());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    /**
     * Gets the users from the firebase that have applied to the job posting created by the current
     * logged user.
     * @param acceptDeclineTasks
     * @param hashMap, list of jobs created by the current logged user.
     */
    public void getUsersFromFirebase(AcceptDeclineTasks acceptDeclineTasks,
                                     Map<String, JobPosting> hashMap) {
        DatabaseReference userReference = db.getReference(User.class.getSimpleName());
        ArrayList<String> emails = (ArrayList<String>) acceptDeclineTasks
                .getAppliedUserEmailsFromHashMap(hashMap);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot adSnapshot : snapshot.getChildren()) {
                    User user = adSnapshot.getValue(User.class);
                    for (String email : emails) {
                        if (email.equals(user.getEmail()) && !acceptDeclineTasks
                                .getUserArrayList().contains(user)) {
                            acceptDeclineTasks.addUserToArray(user);
                        }
                    }
                }
                acceptDeclineTasks.addAcceptDeclineOBJ();
                acceptDeclineTasks.setAdapter(new AcceptDeclineRecyclerAdapter(acceptDeclineTasks,
                        acceptDeclineTasks.getAcceptDeclineOBJList(), hashMap));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

}
