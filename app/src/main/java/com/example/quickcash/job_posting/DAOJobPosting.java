package com.example.quickcash.job_posting;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Initializes a job posting object in the database.
 */
public class DAOJobPosting extends DAO {
    private final DatabaseReference databaseReference;

    /**
     * Constructor for DAOJobPosting Object.
     */
    public DAOJobPosting() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(JobPosting.class.getSimpleName());
    }

    @Override
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /**
     * Adds a databaseReference containing a job posting to the database.
     * @param jobPosting
     * @return
     */
    public Task<Void> add(JobPosting jobPosting) {
        return databaseReference.push().setValue(jobPosting);
    }

    /**
     * Updates a job posting database reference.
     * @param jobPosting
     * @param key
     */
    public void update(JobPosting jobPosting, String key) {
        // Making a hashmap to store the new values
        databaseReference.child(key).setValue(jobPosting);
    }
}
