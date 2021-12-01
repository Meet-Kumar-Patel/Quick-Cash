package com.example.quickcash.UserManagement;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOPreference extends DAO {
    private final DatabaseReference databaseReference;

    public DAOPreference() {
        FirebaseDatabase db = FirebaseDatabase.
                getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(Preference.class.getSimpleName());
    }

    @Override
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    @Override
    public Task<Void> add(IPreference preference) {
        return databaseReference.push().setValue(preference);
    }
}
