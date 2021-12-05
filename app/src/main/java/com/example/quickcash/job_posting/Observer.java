package com.example.quickcash.job_posting;

import com.example.quickcash.user_management.IPreference;

import java.util.ArrayList;

public abstract class Observer {
    public abstract void notifyUsersWithPreferredJobs(IJobPosting jobPosting,
                                                      ArrayList<IPreference> preferences);
}
