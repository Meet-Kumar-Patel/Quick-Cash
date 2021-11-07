package com.example.quickcash.TaskList;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<JobPosting> jobPostingArrayList;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<JobPosting> jobPostingArrayList) {
        this.jobPostingArrayList = jobPostingArrayList;
        this.context = context;
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
        holder.nameText.setText(title);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        Button viewJobButton;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.titletext);
            viewJobButton = view.findViewById(R.id.viewjobbutton);

        }

    }
}
