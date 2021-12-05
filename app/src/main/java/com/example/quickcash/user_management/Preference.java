package com.example.quickcash.user_management;

import com.example.quickcash.common.Constants;
import com.example.quickcash.job_posting.IJobPosting;
import com.example.quickcash.job_posting.Observer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is responsible for implementing the Preference object.
 */
public class Preference extends Observer implements IPreference{


    private String preferenceId;
    private int jobType;
    private int duration;
    private int wage;
    private String employeeName;
    private String employeeEmail;

    /**
     * Preference
     * @param employeeEmail user given employee email
     * @param type user given type
     * @param duration user given duration
     * @param wage user given wage
     * @param employeeName user given employee name
     * @return a preference object
     */
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

    /**
     * getPreferenceId return the preferenceId
     *
     * @return preferenceId
     */
    public String getPreferenceId() {
        return preferenceId;
    }

    /**
     * setPreferenceId set the preferenceId
     *
     * @param preferenceId
     * @return
     */
    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    /**
     * getEmployeeName return the employeeName
     *
     * @return employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * getEmployeeEmail return the employeeEmail
     *
     * @return employeeEmail
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * sending notification to email of users
     */
    @Override
    public void notifyUsersWithPreferredJobs(IJobPosting jobPosting, ArrayList<IPreference> preferences) {
        for (IPreference pref : preferences) {
            boolean jobTypeMatches = pref.getJobType() == jobPosting.getJobType();
            boolean wageEqualOrGreater = jobPosting.getWage() >= pref.getWage();
            boolean durationEqualOrGreater = jobPosting.getDuration() >= pref.getDuration();
            if (jobTypeMatches && wageEqualOrGreater && durationEqualOrGreater) {
                EmailNotification emailNotification = new EmailNotification();
                String employeeEmailPref = pref.getEmployeeEmail();
                emailNotification.sendEmailNotification(Constants.EMAIL_ADDRESS, employeeEmailPref,
                        Constants.SENDER_PASSWORD,
                        Constants.HI + pref.getEmployeeName() +
                                ", " +
                                jobPosting.getCreatedByName() + " " + Constants.CHECK_OUT_DETAILS);

            }
        }
    }

    /**
     * getDuration return the duration
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * setDuration set the duration
     *
     * @param duration
     * @return
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * getWage return the wage
     *
     * @return wage
     */
    public int getWage() {
        return wage;
    }

    /**
     * setWage set the wage
     *
     * @param wage
     * @return
     */
    public void setWage(int wage) {
        this.wage = wage;
    }

    /**
     * getJobType return the jobType
     *
     * @return jobType
     */
    public int getJobType() {
        return jobType;
    }

    /**
     * setJobType set the jobType
     *
     * @param jobType
     * @return
     */
    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

}
