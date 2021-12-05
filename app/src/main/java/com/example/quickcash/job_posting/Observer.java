package com.example.quickcash.job_posting;

import com.example.quickcash.user_management.IPreference;

import java.util.ArrayList;

/**
 * Observer class to notify users when a job is posted that matches their preferences.
 */
public abstract class Observer {
    public abstract void notifyUsersWithPreferredJobs(IJobPosting jobPosting,
                                                      ArrayList<IPreference> preferences);
}
