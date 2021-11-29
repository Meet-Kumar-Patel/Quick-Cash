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
import com.example.quickcash.UserManagement.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineRecyclerAdapter extends RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    private HashMap<String, JobPosting> userHashMap;
    //private ArrayList<User> userArrayList;
    private Context context;
    //Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc
    //
    protected void navigateToRatingsPage() {
        Intent acceptDeclineIntent = new Intent(this, AcceptDeclineRecyclerAdapter.class);
        startActivity(acceptDeclineIntent);
    }

    public AcceptDeclineRecyclerAdapter(Context context, HashMap<String, JobPosting> user) {
        this.userHashMap = user;
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
        User user = userArrayList.get(position);
        String nameFromUser = user.getFirstName() + user.getLastName();
        holder.employeeName.setText(nameFromUser);
        DAOJobPosting acceptdecline = new DAOJobPosting();
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
acceptdecline.update( J, );

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
        return userArrayList.size();
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

            //
            ratingsButton = view.findViewById(R.id.ratingsbutton);
            ratingsButton.setOnClickListener(view -> {
                navigateToRatingsPage();
            });
            acceptButton = view.findViewById(R.id.acceptbutton);
            declineButton = view.findViewById(R.id.declinebutton);

        }

    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }
}
