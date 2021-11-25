package com.example.quickcash.AcceptDeclineTasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    private ArrayList<JobPosting> jobPostingArrayList;
    private ArrayList<JobPosting> jobPostingArrayListFull;
    private Context context;
    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc
    private Filter jobFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<JobPosting> filteredJobList = JobFilter.filter(jobPostingArrayListFull, constraint);
            FilterResults results = new FilterResults();
            results.values = filteredJobList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            jobPostingArrayList.clear();
            jobPostingArrayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public ArrayList<JobPosting> getJobPostingArrayList() {
        return jobPostingArrayList;
    }

    public void setJobPostingArrayList(ArrayList<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = jobPostingArrayList;
    }

    public RecyclerAdapter(Context context, ArrayList<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = jobPostingArrayList;
        jobPostingArrayListFull = new ArrayList<>(jobPostingArrayList);
        this.context = context;
    }

    public RecyclerAdapter() {
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
        String jobTypeString = JobTypeStringGetter.getJobType(jobTypeInt);
        holder.nameText.setText(title);
        holder.locationText.setText(location);
        holder.jobTypeText.setText(jobTypeString);
        holder.viewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jobDetailIntent = new Intent(context, JobPostingDetailsActivity.class);
                jobDetailIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPosting.getJobPostingId());
                context.startActivity(jobDetailIntent);
            }
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

    //Refactor, move to new class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button viewJobButton;
        private TextView nameText;
        private TextView locationText;
        private TextView jobTypeText;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.titletext);
            locationText = view.findViewById(R.id.location);
            jobTypeText = view.findViewById(R.id.jobtype);
            viewJobButton = view.findViewById(R.id.viewjobbutton);

        }

    }
}
