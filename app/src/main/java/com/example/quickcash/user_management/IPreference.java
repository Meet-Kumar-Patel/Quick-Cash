package com.example.quickcash.user_management;

/**
 * This interface is responsible for defining the methods for the IPreference class.
 */
public interface IPreference {

    /**
     * getting employee name
     *
     * @return employee name
     */
    String getEmployeeName();

    /**
     * getting employee email
     *
     * @return employee email
     */
    String getEmployeeEmail();

    /**
     * getting  duration
     *
     * @return duration
     */
    int getDuration();

    /**
     * getting  wage
     *
     * @return wage
     */
    int getWage();

    /**
     * getting job type
     *
     * @return job type
     */
    int getJobType();

    /**
     * Set preference Id
     *
     * @param preferenceId given preference Id
     */
    void setPreferenceId(String preferenceId);

    /**
     * set duration
     *
     * @param duration given duration
     */
    void setDuration(int duration);

    /**
     * set wage
     *
     * @param wage given wage
     */
    void setWage(int wage);

    /**
     * set job type
     *
     * @param jobType given job type
     */
    void setJobType(int jobType);
}
