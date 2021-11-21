package com.example.quickcash.JobPosting;

import androidx.annotation.NonNull;

import com.example.quickcash.UserManagement.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAOJobPosting {
    private DatabaseReference databaseReference;

    public DAOJobPosting() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(JobPosting.class.getSimpleName());
    }

    public com.google.firebase.database.DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add(JobPosting jobPosting) {
        return databaseReference.push().setValue(jobPosting);
    }

    public void update(JobPosting jobPosting) {
        // Making a hashmap to store the new values
        Map<String, Object> newValuesMap = toMap(jobPosting);

        // Get the key of the job posting to update
        String key = databaseReference.orderByChild("jobPostingId") .equalTo(jobPosting.getJobPostingId()).getRef().getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        databaseReference.child(key).setValue(jobPosting);
    }

    public Map<String, Object> toMap(JobPosting jobPosting) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("jobPostingId", jobPosting.getJobPostingId());
        result.put("jobTitle", jobPosting.getJobTitle());
        result.put("jobType", jobPosting.getJobType());
        result.put("location", jobPosting.getLocation());
        result.put("wage", jobPosting.getWage());
        result.put("createdBy", jobPosting.getCreatedBy());
        result.put("isTaskComplete", jobPosting.isTaskComplete());
        result.put("lstAppliedBy", jobPosting.getLstAppliedBy());
        result.put("accepted", jobPosting.getAccepted());
        result.put("createdByName", jobPosting.getCreatedByName());
        return result;
    }

}
