package com.example.quickcash.Ratings;

import com.example.quickcash.UserManagement.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAORating {
    private DatabaseReference databaseReference;

    public DAORating() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(Rating.class.getSimpleName());
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add(Rating rating) {
        return databaseReference.push().setValue(rating);
    }

}
