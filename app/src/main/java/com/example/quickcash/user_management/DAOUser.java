package com.example.quickcash.user_management;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUser extends DAO {
    private final DatabaseReference databaseReference;

    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.
                getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    @Override
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    @Override
    public Task<Void> add(IUser user) {
        return databaseReference.push().setValue(user);
    }

}
