package com.example.quickcash.JobPosting;
import java.util.UUID;
import java.util.ArrayList;


public class JobPosting {

    // Variables
    private String jobPostingId;
    private String jobTitle;
    private int jobType;
    private int duration;
    private String location;
    private int wage;
    private String createdBy;
    private boolean isTaskComplete;
    private ArrayList<String> lstAppliedBy;
    private String accepted;

    private String createdByName;

    public JobPosting(String jobTitle, int type, int duration, String location, int wage, String createdBy, String createdByName) {
        this.jobPostingId = UUID.randomUUID().toString();
        this.jobTitle = jobTitle;
        this.jobType = type;
        this.duration = duration;
        this.location = location;
        this.wage = wage;
        this.createdBy = createdBy;
        this.isTaskComplete = false;
        this.lstAppliedBy = new ArrayList();
        this.accepted = "";
        this.createdByName = createdByName;
    }

    public JobPosting() {
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

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }

    public ArrayList getLstAppliedBy() {
        return lstAppliedBy;
    }

    public void setLstAppliedBy(ArrayList<String> lstAppliedBy) {
        this.lstAppliedBy = lstAppliedBy;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
}
