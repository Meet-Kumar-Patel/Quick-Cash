package com.example.quickcash.JobPosting;

import com.example.quickcash.UserManagement.IPreference;

import java.util.ArrayList;

public abstract class Observer {
    public abstract int getJobType();
    public abstract String getEmployeeEmail();
    public abstract void notifyUsersWithPreferredJobs(IJobPosting jobPosting,
                                                      ArrayList<IPreference> preferences);
}
