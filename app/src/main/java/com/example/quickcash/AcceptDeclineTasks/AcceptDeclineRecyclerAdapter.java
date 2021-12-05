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
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.Ratings.GiveRatingsActivity;
import com.example.quickcash.Ratings.ViewRatingActivity;
import com.example.quickcash.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptDeclineRecyclerAdapter extends RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    HashMap<String, JobPosting> hashMap;
    private ArrayList<AcceptDeclineObject> acceptDeclineObjects;
    private Context context;

    /**
     * Initializes a recycler adapter to display a list of employees to be accepted/declined.
     * Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc
     * @param context
     * @param acceptDeclineObjects
     * @param hashMap
     */
    public AcceptDeclineRecyclerAdapter(Context context, List<AcceptDeclineObject> acceptDeclineObjects, Map<? extends Object, ? extends Object> hashMap) {
        this.acceptDeclineObjects = (ArrayList<AcceptDeclineObject>) acceptDeclineObjects;
        this.context = context;
        this.hashMap = (HashMap<String, JobPosting>) hashMap;
    }

    public AcceptDeclineRecyclerAdapter() {
    }

    /**
     * Initializes the view holder for the recycler adapter.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.accept_decline_items, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Initializes each of the elements inside of the view holder on binding.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AcceptDeclineObject acceptDeclineObject = acceptDeclineObjects.get(position);
        holder.employeeName.setText(acceptDeclineObject.getUserName());
        holder.jobTitleButton.setText(acceptDeclineObject.getJobPostingName());
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        String jpKey = acceptDeclineObject.getJobKey();
        JobPosting jobPosting = hashMap.get(jpKey);
        holder.acceptButton.setOnClickListener(view -> {
            jobPosting.setAccepted(acceptDeclineObject.getUserEmail());
            daoJobPosting.update(jobPosting, jpKey);
            disableAcceptBtn(holder);
        });
        // If the candidate has been accepted then the btn should say Selected and should not be
        // clickable
        if (acceptDeclineObject.isAccepted()) {
            disableAcceptBtn(holder);
        }
        holder.ratingsButton.setOnClickListener(view -> openIntent(jpKey,
                acceptDeclineObject.getUserName(), acceptDeclineObject.getUserEmail()));
    }

    /**
     * Disables the accept button and changes display text so employee can't be accepted twice.
     * @param holder
     */
    public void disableAcceptBtn(MyViewHolder holder) {
        holder.acceptButton.setClickable(false);
        holder.acceptButton.setText(Constants.ACCEPT_BUTTON_DISABLE_TEXT);
    }

    /**
     * Returns the number of acceptdecline objects in the arraylist
     * @return
     */
    @Override
    public int getItemCount() {
        return acceptDeclineObjects.size();
    }

    public List<AcceptDeclineObject> getAcceptDeclineArraylist() {
        return acceptDeclineObjects;
    }

    public void setAcceptDeclineArrayList(List<AcceptDeclineObject> acceptDeclineObjects) {
        this.acceptDeclineObjects = (ArrayList<AcceptDeclineObject>) acceptDeclineObjects;
    }

    /**
     * Initializes intents
     * @param key
     * @param userName
     * @param userEmail
     */
    public void openIntent(String key, String userName, String userEmail) {
        JobPosting jobPosting = hashMap.get(key);
        Intent intent;
        if (jobPosting.isTaskComplete()) {
            intent = new Intent(context, GiveRatingsActivity.class);
        } else {
            intent = new Intent(context, ViewRatingActivity.class);
        }
        intent.putExtra(JobPostingActivity.EXTRA_MESSAGE, userEmail);
        intent.putExtra("jobPostingID", jobPosting.getJobPostingId());
        intent.putExtra("userToRate", userName);
        intent.putExtra("page", "acceptDecline");
        context.startActivity(intent);
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


}
