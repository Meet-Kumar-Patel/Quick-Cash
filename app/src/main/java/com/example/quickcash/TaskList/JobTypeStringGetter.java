package com.example.quickcash.TaskList;

import com.example.quickcash.common.Constants;

public class JobTypeStringGetter {
    public static final String HOURLY_BABYSITTING = "Hourly Babysitting";

    public static String getJobType(int jobType) {
        String jobTypeString = "";
        switch (jobType) {
            case 0:
                jobTypeString = Constants.REPAIRING_COMPUTER;
                break;
            case 1:
                jobTypeString = Constants.MOWING_THE_LAWN;
                break;
            case 2:
                jobTypeString = Constants.DESIGN_WEBSITE;
                break;
            case 3:
                jobTypeString = Constants.WALKING_A_DOG;
                break;
            case 4:
                jobTypeString = HOURLY_BABYSITTING;
                break;
            case 5:
                jobTypeString = Constants.PICKING_UP_GROCERY;
                break;
            default:
                throw new IllegalStateException(Constants.UNEXPECTED_JOBTYPE + jobType);
        }
        return jobTypeString;
    }
}
