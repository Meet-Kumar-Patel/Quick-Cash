package com.example.quickcash.TaskList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.JobPosting.JobTypeStringGetter;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.List;

public class TaskListRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerViewHolder> implements Filterable {

    private ArrayList<JobPosting> jobPostingArrayList;
    private Context context;
    private Filter jobFilter;

    /**
     * Initializes a recycler adapter to display job postings.
     * Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc
     *
     * @param context
     * @param jobPostingArrayList
     */
    public TaskListRecyclerAdapter(Context context, List<JobPosting> jobPostingArrayList) {
        this.context = context;
        this.jobPostingArrayList = (ArrayList<JobPosting>) jobPostingArrayList;
        ArrayList<JobPosting> jobPostingArrayListFull = new ArrayList<>(jobPostingArrayList);
        TaskListFilterFactory taskListFilterFactory = new TaskListFilterFactory();
        jobFilter = taskListFilterFactory.getFilter(this, jobPostingArrayListFull);
    }

    public TaskListRecyclerAdapter() {
    }

    /**
     * Initializes the view holder for the recycler adapter.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public TaskRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new TaskRecyclerViewHolder(itemView);
    }

    /**
     * Initializes each of the elements inside of the view holder on binding.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewHolder holder, int position) {
        JobPosting jobPosting = jobPostingArrayList.get(position);
        String title = jobPosting.getJobTitle();
        String location = jobPosting.getLocation();
        int jobTypeInt = jobPosting.getJobType();
        String jobTypeString = JobTypeStringGetter.getJobType(jobTypeInt);
        holder.getNameText().setText(title);
        holder.getLocationText().setText(location);
        holder.getJobTypeText().setText(jobTypeString);
        holder.getViewJobButton().setOnClickListener(view -> {
            Intent jobDetailIntent = new Intent(context, JobPostingDetailsActivity.class);
            jobDetailIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPosting.getJobPostingId());
            context.startActivity(jobDetailIntent);
        });
    }

    /**
     * Returns the number of job postings being displayed.
     *
     * @return size of Job Posting List.
     */
    @Override
    public int getItemCount() {
        return jobPostingArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return jobFilter;
    }

    public List<JobPosting> getJobPostingArrayList() {
        return jobPostingArrayList;
    }

    /**
     * Sets the JobPosting List, and updates the contents of the recycler view.
     *
     * @param jobPostingArrayList
     */
    public void setJobPostingArrayList(List<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = (ArrayList<JobPosting>) jobPostingArrayList;
        notifyDataSetChanged();
    }

}
