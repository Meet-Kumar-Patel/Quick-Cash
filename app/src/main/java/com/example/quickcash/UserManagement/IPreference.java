package com.example.quickcash.UserManagement;

public interface IPreference {

    String getPreferenceId();

    void setPreferenceId(String preferenceId);

    String getEmployeeName();

    String getEmployeeEmail();

    int getDuration();

    void setDuration(int duration);

    int getWage();

    void setWage(int wage);

    int getJobType();

    void setJobType(int jobType);
}
