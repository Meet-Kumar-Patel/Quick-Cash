package com.example.quickcash.JobPosting;
import java.util.UUID;
import java.util.UUID.*;
import java.util.ArrayList;


public class JobPosting {

    // Variables
    private String jobPostingId;
    private String jobTitle;
    private int duration;
    private String location;
    private double wage;
    private int createdById;
    private boolean isTaskComplete;
    private ArrayList lstAppliedByIds;
    private int acceptedId;

    public JobPosting(String jobTitle, int duration, String location, double wage, int createdById, boolean isTaskComplete, ArrayList lstAppliedByIds, int acceptedId) {
        this.jobPostingId = UUID.randomUUID().toString();
        this.jobTitle = jobTitle;
        this.duration = duration;
        this.location = location;
        this.wage = wage;
        this.createdById = createdById;
        this.isTaskComplete = isTaskComplete;
        this.lstAppliedByIds = lstAppliedByIds;
        this.acceptedId = acceptedId;
    }

    public String getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(String jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    // Getter and Setters
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }

    public ArrayList getLstAppliedByIds() {
        return lstAppliedByIds;
    }

    public void setLstAppliedByIds(ArrayList lstAppliedByIds) {
        this.lstAppliedByIds = lstAppliedByIds;
    }

    public int getAcceptedId() {
        return acceptedId;
    }

    public void setAcceptedId(int acceptedId) {
        this.acceptedId = acceptedId;
    }
}
