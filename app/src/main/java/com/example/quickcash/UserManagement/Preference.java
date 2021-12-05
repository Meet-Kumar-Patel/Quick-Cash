package com.example.quickcash.UserManagement;

import com.example.quickcash.JobPosting.IJobPosting;
import com.example.quickcash.JobPosting.Observer;
import com.example.quickcash.common.Constants;

import java.util.ArrayList;
import java.util.UUID;

public class Preference extends Observer implements IPreference{
    private String preferenceId;
    private int jobType;
    private int duration;
    private int wage;
    private String employeeName;
    private String employeeEmail;

    public Preference(String employeeEmail, int type, int duration, int wage,
                      String employeeName) {
        this.preferenceId = UUID.randomUUID().toString();
        this.jobType = type;
        this.duration = duration;
        this.wage = wage;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
    }

    public Preference() {
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    @Override
    public void notifyUsersWithPreferredJobs(IJobPosting jobPosting, ArrayList<IPreference> preferences) {
        for (IPreference pref : preferences) {
            if (pref.getJobType() == jobPosting.getJobType() && pref.getWage() >= jobPosting.getWage() && pref.getDuration() >= jobPosting.getDuration()) {
                EmailNotification emailNotification = new EmailNotification();
                String employeeEmail = pref.getEmployeeEmail();
                emailNotification.sendEmailNotification(Constants.EMAIL_ADDRESS, employeeEmail, Constants.SENDER_PASSWORD, Constants.HI +pref.getEmployeeName()+ Constants.THE_FOLLOWING_EMPLOYER +jobPosting.getCreatedByName() + Constants.CHECK_OUT_DETAILS);

            }
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

}
