package com.example.quickcash.TaskList;

import android.widget.Filter;

import com.example.quickcash.JobPosting.JobPosting;

import java.util.ArrayList;
import java.util.List;

public class TaskListFilterFactory {

    public Filter getFilter(TaskListRecyclerAdapter taskListRecyclerAdapter,
                            List<JobPosting> fullJobPostingList) {
        return new Filter() {
            TaskListFilterFactory taskListFilterFactory = new TaskListFilterFactory();
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<JobPosting> filteredJobList =
                        taskListFilterFactory.filterJobPostingList(fullJobPostingList, constraint);
                FilterResults results = new FilterResults();
                results.values = filteredJobList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<JobPosting> jobPostingList = taskListRecyclerAdapter.getJobPostingArrayList();
                jobPostingList.clear();
                jobPostingList.addAll((List<JobPosting>) filterResults.values);
                taskListRecyclerAdapter.setJobPostingArrayList(jobPostingList);
            }
        };
    }

    public List<JobPosting> filterJobPostingList(List<JobPosting> fullJobPostingList,
                                                 CharSequence constraint) {
        List<JobPosting> filteredJobList = new ArrayList<>();
        if (constraint == null || constraint.length() == 0) {
            filteredJobList.addAll(fullJobPostingList);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (JobPosting jobPosting : fullJobPostingList) {
                String jobTitle = jobPosting.getJobTitle().toLowerCase();
                String jobType = JobTypeStringGetter.
                        getJobType(jobPosting.getJobType()).toLowerCase();
                if (jobTitle.contains(filterPattern) || jobType.contains(filterPattern)) {
                    filteredJobList.add(jobPosting);
                }
            }
        }
        return filteredJobList;
    }
}
