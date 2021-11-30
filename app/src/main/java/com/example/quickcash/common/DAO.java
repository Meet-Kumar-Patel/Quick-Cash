package com.example.quickcash.common;

import com.example.quickcash.UserManagement.IPreference;
import com.example.quickcash.UserManagement.IUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public abstract class DAO {
    public DatabaseReference getDatabaseReference() {
        return null;
    }

    public Task<Void> add(IUser user) {
        return null;
    }

    public Task<Void> add(IPreference preference) {
        return null;
    }
}
