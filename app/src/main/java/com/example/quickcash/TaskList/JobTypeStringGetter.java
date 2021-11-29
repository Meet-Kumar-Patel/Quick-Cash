package com.example.quickcash.TaskList;

public class JobTypeStringGetter {
    public static String getJobType(int jobType) {
        String jobTypeString = "";
        switch (jobType) {
            case 0:
                jobTypeString = "Repairing Computer";
                break;
            case 1:
                jobTypeString = "Mowing the Lawn";
                break;
            case 2:
                jobTypeString = "Design Website";
                break;
            case 3:
                jobTypeString = "Walking A Dog";
                break;
            case 4:
                jobTypeString = "Hourly Babysitting";
                break;
            case 5:
                jobTypeString = "Picking Up Grocery";
                break;
        }
        return jobTypeString;
    }
}
