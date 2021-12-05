package com.example.quickcash.JobPosting;

import com.example.quickcash.UserManagement.IPreference;

import java.util.ArrayList;

public abstract class Observer {
    public abstract void notifyUsersWithPreferredJobs(IJobPosting jobPosting,
                                                      ArrayList<IPreference> preferences);
}
