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

import com.example.quickcash.Dashboard.EmployeeDashboardActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.List;

public class TaskListRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerViewHolder> implements Filterable {

    private ArrayList<JobPosting> jobPostingArrayList;
    private Context context;
    private Filter jobFilter;

    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc

    public TaskListRecyclerAdapter(Context context, List<JobPosting> jobPostingArrayList) {
        this.context = context;
        this.jobPostingArrayList = (ArrayList<JobPosting>) jobPostingArrayList;
        ArrayList<JobPosting> jobPostingArrayListFull = new ArrayList<>(jobPostingArrayList);
        TaskListFilterFactory taskListFilterFactory = new TaskListFilterFactory();
        jobFilter = taskListFilterFactory.getFilter(this, jobPostingArrayListFull);
    }

    public TaskListRecyclerAdapter(EmployeeDashboardActivity employeeDashboardActivity, List<JobPosting> jobsAppliedForArray) {
    }

    @NonNull
    @Override
    public TaskRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new TaskRecyclerViewHolder(itemView);
    }

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

    public void setJobPostingArrayList(List<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = (ArrayList<JobPosting>) jobPostingArrayList;
        notifyDataSetChanged();
    }

}
