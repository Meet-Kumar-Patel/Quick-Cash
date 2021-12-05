package com.example.quickcash.job_posting;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobPosting implements IJobPosting {
    private final String jobPostingId;
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
        this.lstAppliedBy = new ArrayList<>();
        this.accepted = "";
        this.createdByName = createdByName;
    }

    public JobPosting() {
        this.jobPostingId = UUID.randomUUID().toString();
    }

    @Override
    public String getJobPostingId() {
        return jobPostingId;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int getWage() {
        return wage;
    }

    @Override
    public void setWage(int wage) {
        this.wage = wage;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    @Override
    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }

    @Override
    public List<String> getLstAppliedBy() {
        if (lstAppliedBy == null) {
            return new ArrayList<>();
        }
        return lstAppliedBy;
    }

    @Override
    public void setLstAppliedBy(List<String> lstAppliedBy) {
        this.lstAppliedBy = (ArrayList) lstAppliedBy;
    }

    @Override
    public String getAccepted() {
        return accepted;
    }

    @Override
    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    @Override
    public int getJobType() {
        return jobType;
    }

    @Override
    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    @Override
    public String getCreatedByName() {
        return createdByName;
    }

    @Override
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

}

