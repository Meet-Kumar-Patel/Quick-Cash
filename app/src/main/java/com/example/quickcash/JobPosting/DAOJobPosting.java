package com.example.quickcash.JobPosting;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOJobPosting extends DAO {
    private DatabaseReference databaseReference;

    public DAOJobPosting() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
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
