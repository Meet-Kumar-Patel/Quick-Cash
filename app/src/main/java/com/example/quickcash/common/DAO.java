package com.example.quickcash.common;

import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.user_management.IPreference;
import com.example.quickcash.user_management.IUser;
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

    public Task<Void> add(JobPosting jobPosting) {
        return null;
    }

}
