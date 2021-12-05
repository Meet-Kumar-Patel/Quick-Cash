package com.example.quickcash.ratings;

import com.example.quickcash.common.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAORating {
    private final DatabaseReference databaseReference;

    public DAORating() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(Rating.class.getSimpleName());
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add(Rating rating) {
        return databaseReference.push().setValue(rating);
    }

}
