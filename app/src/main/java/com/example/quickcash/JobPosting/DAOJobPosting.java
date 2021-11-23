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

    public void update(JobPosting jobPosting, String key) {
        // Making a hashmap to store the new values
        databaseReference.child(key).setValue(jobPosting);
    }
}