package com.example.quickcash;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOJobTypes {

    private DatabaseReference databaseReference;

    public DAOJobTypes() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Will Get all.
    }

    public Task<Void> add(JobType jobType) {
        return databaseReference.push().setValue(jobType);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }
}
