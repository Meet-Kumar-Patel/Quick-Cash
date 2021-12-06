package com.example.quickcash.accept_decline_tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.job_posting.DAOJobPosting;
import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.job_posting.JobPostingActivity;
import com.example.quickcash.paypal.PaypalActivity;
import com.example.quickcash.R;
import com.example.quickcash.ratings.GiveRatingsActivity;
import com.example.quickcash.ratings.ViewRatingActivity;
import com.example.quickcash.user_management.EmailNotification;
import com.example.quickcash.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptDeclineRecyclerAdapter extends
        RecyclerView.Adapter<AcceptDeclineRecyclerAdapter.MyViewHolder> {

    HashMap<String, JobPosting> hashMap;
    private ArrayList<AcceptDeclineObject> acceptDeclineObjects;
    private Context context;

    /**
     * Initializes a recycler adapter to display a list of employees to be accepted/declined.
     * Code adapted from https://www.youtube.com/watch?v=sJ-Z9G0SDhc
     *
     * @param context
     * @param acceptDeclineObjects
     * @param hashMap
     */
    public AcceptDeclineRecyclerAdapter(Context context, List<AcceptDeclineObject>
            acceptDeclineObjects, Map<? extends Object, ? extends Object> hashMap) {
        this.acceptDeclineObjects = (ArrayList<AcceptDeclineObject>) acceptDeclineObjects;
        this.context = context;
        this.hashMap = (HashMap<String, JobPosting>) hashMap;
    }

    public AcceptDeclineRecyclerAdapter() {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.accept_decline_items, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Initializes each of the elements inside of the view holder on binding.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Initializing the job list items
        AcceptDeclineObject acceptDeclineObject = acceptDeclineObjects.get(position);
        holder.employeeName.setText(acceptDeclineObject.getUserName());
        holder.jobTitleButton.setText(acceptDeclineObject.getJobPostingName());
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        String jpKey = acceptDeclineObject.getJobKey();
        JobPosting jobPosting = hashMap.get(jpKey);
        // When the user clicks on the accept btn, update the database
        holder.acceptButton.setOnClickListener(view -> {
            jobPosting.setAccepted(acceptDeclineObject.getUserEmail());
            //Notify employee when employer accepts the employee for the job.
            EmailNotification emailNotification = new EmailNotification();
            String employeeEmail = acceptDeclineObject.getUserEmail();
            emailNotification.sendEmailNotification(Constants.EMAIL_ADDRESS, employeeEmail,
                    Constants.SENDER_PASSWORD, Constants.HI +
                            acceptDeclineObject.getUserName() +
                            Constants.CONGRATULATIONS_YOU_HAVE_BEEN_ACCEPTED);
            daoJobPosting.update(jobPosting, jpKey);
            disableAcceptBtn(holder);
        });

        // If the candidate has been accepted then the btn should say Selected and should not be
        // clickable
        if (acceptDeclineObject.isAccepted()) {
            disableAcceptBtn(holder);
        }
        if (acceptDeclineObject.isTaskComplete()) {
            showPayBtn(holder, jpKey, acceptDeclineObject.getUserName());
        }
        holder.ratingsButton.setOnClickListener(view -> openIntent(jpKey,
                acceptDeclineObject.getUserName(), acceptDeclineObject.getUserEmail()));
    }

    /**
     * Disables the accept button and changes display text so employee can't be accepted twice.
     *
     * @param holder
     */
    public void disableAcceptBtn(MyViewHolder holder) {
        holder.acceptButton.setClickable(false);
        holder.acceptButton.setText(Constants.ACCEPT_BUTTON_DISABLE_TEXT);
    }

    /**
     * Allows the employer to check the invoice page for paying the employees
     * @param holder, is the adapter holding the btn
     * @param key, is the location string in firebase real time database
     * @param userName, is the name of the receiver of the payment.
     */
    public void showPayBtn(MyViewHolder holder, String key, String userName) {
        holder.acceptButton.setClickable(true);
        holder.acceptButton.setText("Pay Now");
        holder.acceptButton.setOnClickListener(view -> openPayPalActivity(key, userName));
    }

    /**
     * Returns the number of acceptdecline objects in the arraylist
     *
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
     * Initializes rating intents. If the task is completed GiveRatingsActivity page is opened, else
     * the employer can only view the rating.
     * @param key, is the location in the database
     * @param userName, is the name of the user receiving the payment
     * @param userEmail, is the email of the user receiving the email
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

    /**
     * Opens the paypal activity and it passes the key and name of the user receiving the payment.
     * @param key, the location in the database
     * @param userName, is the name of the user receiving the payment
     */
    public void openPayPalActivity(String key, String userName) {
        JobPosting jobPosting = hashMap.get(key);
        Intent intent = new Intent(context, PaypalActivity.class);

        intent.putExtra("userName", userName);
        intent.putExtra("jobID", jobPosting.getJobPostingId());
        context.startActivity(intent);
    }

    /**
     * The methods initializes the buttons of the holder
     */
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
