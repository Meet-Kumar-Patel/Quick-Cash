package com.example.quickcash.Paypal;

import com.example.quickcash.JobPosting.JobPosting;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOInvoice {
    private DatabaseReference databaseReference;

    public DAOInvoice() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(Invoice.class.getSimpleName());
    }

    public com.google.firebase.database.DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add(JobPosting jobPosting) {
        return databaseReference.push().setValue(jobPosting);
    }
}
