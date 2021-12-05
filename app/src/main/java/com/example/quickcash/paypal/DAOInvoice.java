package com.example.quickcash.paypal;

import com.example.quickcash.common.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOInvoice {
    private DatabaseReference databaseReference;

    public DAOInvoice() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(Invoice.class.getSimpleName());
    }

    public com.google.firebase.database.DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add(Invoice invoice) {
        return databaseReference.push().setValue(invoice);
    }
}
