package com.example.quickcash.JobPosting;

import java.util.List;

public interface IJobPosting {
    String getJobPostingId();

    String getJobTitle();

    void setJobTitle(String jobTitle);

    int getDuration();

    void setDuration(int duration);

    String getLocation();

    void setLocation(String location);

    int getWage();

    void setWage(int wage);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    boolean isTaskComplete();

    void setTaskComplete(boolean taskComplete);

    List<String> getLstAppliedBy();

    void setLstAppliedBy(List<String> lstAppliedBy);

    String getAccepted();

    void setAccepted(String accepted);

    int getJobType();

    void setJobType(int jobType);

    String getCreatedByName();

    void setCreatedByName(String createdByName);

    public void attach(Observer user);

    public void notifyAllEmployee(int jobType);
}
