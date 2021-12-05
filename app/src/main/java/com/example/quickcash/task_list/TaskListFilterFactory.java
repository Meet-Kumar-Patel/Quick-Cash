package com.example.quickcash.task_list;

import android.widget.Filter;

import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.job_posting.JobTypeStringGetter;

import java.util.ArrayList;
import java.util.List;

public class TaskListFilterFactory {

    /**
     * Creates and returns a filter to be used to search through job postings.
     *
     * @param taskListRecyclerAdapter
     * @param fullJobPostingList
     * @return
     */
    public Filter getFilter(TaskListRecyclerAdapter taskListRecyclerAdapter,
                            List<JobPosting> fullJobPostingList) {
        return new Filter() {
            final TaskListFilterFactory taskListFilterFactory = new TaskListFilterFactory();

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

    /**
     * Filters a List of JobPostings according to either their name or job type.
     *
     * @param fullJobPostingList
     * @param constraint
     * @return
     */
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
