package com.example.quickcash.TaskList;

import com.example.quickcash.JobPosting.JobPosting;

import java.util.ArrayList;

public class TaskListJobFilter {
    public static ArrayList<JobPosting> filter(ArrayList<JobPosting> jobPostingArrayListFull, CharSequence constraint) {
        ArrayList<JobPosting> filteredJobList = new ArrayList<>();
        if (constraint == null || constraint.length() == 0) {
            filteredJobList.addAll(jobPostingArrayListFull);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (JobPosting jobPosting : jobPostingArrayListFull) {
                String jobTitle = jobPosting.getJobTitle().toLowerCase();
                String jobType = JobTypeStringGetter.getJobType(jobPosting.getJobType()).toLowerCase();
                if (jobTitle.contains(filterPattern) || jobType.contains(filterPattern)) {
                    filteredJobList.add(jobPosting);
                }
            }
        }
        return filteredJobList;
    }
}
