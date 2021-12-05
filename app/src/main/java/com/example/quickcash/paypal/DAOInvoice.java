package com.example.quickcash.paypal;

import com.example.quickcash.common.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Pushes an instance of an invoice to the firebase database.
 */
public class DAOInvoice {
    private DatabaseReference databaseReference;

    /**
     * Constructor for DAOInvoice
     */
    public DAOInvoice() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(Invoice.class.getSimpleName());
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /**
     * Adds an invoice to the database
     * @param invoice
     * @return
     */
    public Task<Void> add(Invoice invoice) {
        return databaseReference.push().setValue(invoice);
    }
}
