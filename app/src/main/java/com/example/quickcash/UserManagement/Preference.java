package com.example.quickcash.UserManagement;

import java.util.UUID;


public class Preference {

    private String preferenceId;
    private int jobType;
    private int duration;

    private int wage;
    private String employeeName;


    public Preference(int type, int duration, int wage, String employeeName) {
        this.preferenceId = UUID.randomUUID().toString();
        this.jobType = type;
        this.duration = duration;

        this.wage = wage;
        this.employeeName = employeeName;
    }

    public Preference() {
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getemployeeName() {
        return employeeName;
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
