package com.example.quickcash.AcceptDeclineTasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.DAOJobPosting;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.Ratings.GiveRatingsActivity;
import com.example.quickcash.Ratings.ViewRatingActivity;
import com.example.quickcash.UserManagement.EmailNotification;

import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineRecyclerAdapter extends RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    private ArrayList<AcceptDeclineObject> acceptDeclineObjects;
    private Context context;
    HashMap<String, JobPosting> hashMap;
    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc

    public AcceptDeclineRecyclerAdapter(Context context, ArrayList<AcceptDeclineObject> acceptDeclineObjects, HashMap hashMap) {
        this.acceptDeclineObjects = acceptDeclineObjects;
        this.context = context;
        this.hashMap = hashMap;
    }

    public AcceptDeclineRecyclerAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.accept_decline_items, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AcceptDeclineObject acceptDeclineObject = acceptDeclineObjects.get(position);
        holder.employeeName.setText(acceptDeclineObject.getUserName());
        holder.jobTitleButton.setText(acceptDeclineObject.getJobPostingName());
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        String jpKey = acceptDeclineObject.getJobKey();
        JobPosting jobPosting =  hashMap.get(jpKey);
        holder.acceptButton.setOnClickListener(view -> {
            jobPosting.setAccepted(acceptDeclineObject.getUserEmail());

// --------------- Notify employee when employer accepts the employee for the job.//----------------
            EmailNotification emailNotification = new EmailNotification();
            String employeeEmail = acceptDeclineObject.getUserEmail();
            emailNotification.sendEmailNotification("noreplycsci3130@gmail.com",employeeEmail,"Joben@1999","Hi "+ acceptDeclineObject.getUserName() +", Congratulations! You have been accepted for a job posting. Please login to check out details");

            daoJobPosting.update(jobPosting, jpKey);
            disableAcceptBtn(holder);
        });

        // If the candidate has been accepted then the btn should say Selected and should not be clickable
        if(acceptDeclineObject.isAccepted()) {disableAcceptBtn(holder);}


        holder.ratingsButton.setOnClickListener(view -> openIntent(jpKey, acceptDeclineObject.getUserName(), acceptDeclineObject.getUserEmail()));
    }

    public void disableAcceptBtn(MyViewHolder holder) {
            holder.acceptButton.setClickable(false);
            holder.acceptButton.setText("Selected");
    }

    @Override
    public int getItemCount() {
        return acceptDeclineObjects.size();
    }

    //Refactor, move to new class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button acceptButton;
        Button ratingsButton;
        TextView employeeName;
        TextView jobTitleButton;

        public MyViewHolder(final View view) {
            super(view);
            employeeName = view.findViewById(R.id.employeename);
            ratingsButton = view.findViewById(R.id.ratingsbutton);
            acceptButton = view.findViewById(R.id.acceptbutton);
            jobTitleButton = view.findViewById(R.id.txt_job_title);
        }
    }

    public ArrayList<AcceptDeclineObject> getAcceptDeclineArraylist() { return acceptDeclineObjects;}

    public void setAcceptDeclineArrayList(ArrayList<AcceptDeclineObject> acceptDeclineObjects) {this.acceptDeclineObjects = acceptDeclineObjects; }

    public void openIntent(String key, String userName, String userEmail) {
        JobPosting jobPosting = hashMap.get(key);
        Intent intent;
        if(jobPosting.isTaskComplete()) {
            intent = new Intent(context, GiveRatingsActivity.class);
        }
        else {
            intent = new Intent(context, ViewRatingActivity.class);
        }
        intent.putExtra(JobPostingActivity.EXTRA_MESSAGE, userEmail);
        intent.putExtra("jobPostingID", jobPosting.getJobPostingId());
        intent.putExtra("userToRate", userName);
        intent.putExtra("page", "acceptDecline");
        context.startActivity(intent);
    }


}
