package com.example.quickcash;

public class JobType {
    private String key;
    private String jobType;

    public JobType(){}

    public JobType(String key, String type) {
        this.key = key;
        this.jobType = type;
    }

    public String getKey() {
        return key;
    }

    public String getJobType() {
        return jobType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
