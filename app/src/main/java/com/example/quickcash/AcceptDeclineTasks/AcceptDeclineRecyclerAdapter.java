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

import com.example.quickcash.JobPosting.DAOJobPosting;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.EmployerDashboardActivity;
import com.example.quickcash.UserManagement.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineRecyclerAdapter extends RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    private ArrayList<AcceptDeclineObject> acceptDeclineObjects;
    private Context context;
    private AcceptDeclineTasks acceptDeclineTasks= new AcceptDeclineTasks();
    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc

    public AcceptDeclineRecyclerAdapter(Context context, ArrayList<AcceptDeclineObject> acceptDeclineObjects) {
        this.acceptDeclineObjects = acceptDeclineObjects;
        this.context = context;
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
        String nameFromUser = acceptDeclineObject.getUserName();
        holder.employeeName.setText(nameFromUser);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        String jpKey = acceptDeclineObject.getJobKey();
        JobPosting jobPosting =  acceptDeclineTasks.getJobPosting(acceptDeclineObject.getJobKey());
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobPosting.setAccepted(acceptDeclineObject.getUserEmail());
                daoJobPosting.update(jobPosting, jpKey);
            }
        });

        holder.ratingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptDeclineTasks.openIntent(jpKey);
            }
        });
    }

    @Override
    public int getItemCount() {
        return acceptDeclineObjects.size();
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
            ratingsButton = view.findViewById(R.id.ratingsbutton);
            acceptButton = view.findViewById(R.id.acceptbutton);
        }
    }

    public ArrayList<AcceptDeclineObject> getUserArrayList() {
        return acceptDeclineObjects;
    }

    public void setUserArrayList(ArrayList<AcceptDeclineObject> acceptDeclineObjects) {
        this.acceptDeclineObjects = acceptDeclineObjects;
    }
}
