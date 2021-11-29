package com.example.quickcash.AcceptDeclineTasks;

import java.util.UUID;

public class AcceptDeclineObject {
    private final String ID;
    private String userName;
    private String userEmail;
    private String jobPostingID;
    private String jobPostingName;
    private String jobKey;


    public AcceptDeclineObject(String userName, String userEmail, String jobPostingID, String jobPostingName, String jobKey) {
        ID = UUID.randomUUID().toString();
        this.userName = userName;
        this.userEmail = userEmail;
        this.jobPostingID = jobPostingID;
        this.jobPostingName = jobPostingName;
        this.jobKey = jobKey;
    }

    public String getID() {
        return ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJobPostingID() {
        return jobPostingID;
    }

    public void setJobPostingID(String jobPostingID) {
        this.jobPostingID = jobPostingID;
    }

    public String getJobPostingName() {
        return jobPostingName;
    }

    public void setJobPostingName(String jobPostingName) {
        this.jobPostingName = jobPostingName;
    }
    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
