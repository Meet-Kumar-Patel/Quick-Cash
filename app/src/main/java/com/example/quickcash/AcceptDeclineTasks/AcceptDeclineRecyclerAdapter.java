package com.example.quickcash.AcceptDeclineTasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;

import java.util.ArrayList;

public class AcceptDeclineRecyclerAdapter extends RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    private ArrayList<JobPosting> jobPostingArrayList;
    private ArrayList<JobPosting> jobPostingArrayListFull;
    private Context context;
    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc

    public ArrayList<JobPosting> getJobPostingArrayList() {
        return jobPostingArrayList;
    }

    public void setJobPostingArrayList(ArrayList<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = jobPostingArrayList;
    }

    public AcceptDeclineRecyclerAdapter(Context context, ArrayList<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = jobPostingArrayList;
        jobPostingArrayListFull = new ArrayList<>(jobPostingArrayList);
        this.context = context;
    }

    public AcceptDeclineRecyclerAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JobPosting jobPosting = jobPostingArrayList.get(position);
        String title = jobPosting.getJobTitle();
        String location = jobPosting.getLocation();
        int jobTypeInt = jobPosting.getJobType();
        holder.employeeName.setText(title);
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.ratingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return jobPostingArrayList.size();
    }

    //Refactor, move to new class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button acceptButton;
        Button declineButton;
        Button ratingsButton;
        private TextView employeeName;

        public MyViewHolder(final View view) {
            super(view);
            employeeName = view.findViewById(R.id.employeename);
            ratingsButton = view.findViewById(R.id.location);
            acceptButton = view.findViewById(R.id.jobtype);
            declineButton = view.findViewById(R.id.viewjobbutton);

        }

    }
}
