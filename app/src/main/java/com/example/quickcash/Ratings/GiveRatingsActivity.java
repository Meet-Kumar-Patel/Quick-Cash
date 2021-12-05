package com.example.quickcash.Ratings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.AcceptDeclineTasks.AcceptDeclineTasks;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiveRatingsActivity extends AppCompatActivity {

    private TextView statusView;
    private String senderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);
        Intent giveRatingIntent = getIntent();
        String receiverEmail = giveRatingIntent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        String jobPostingID = giveRatingIntent.getStringExtra("jobPostingID");
        String userToRate = giveRatingIntent.getStringExtra("userToRate");
        String page = giveRatingIntent.getStringExtra("page");
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        senderEmail = sessionManager.getKeyEmail();
        RatingBar starRatingBar = findViewById(R.id.star_rating_bar);
        initializeHeader(userToRate);
        initializeStatusView();
        initializeRatingButton(receiverEmail, jobPostingID, starRatingBar);
        initializeBackButton(jobPostingID, page);
    }

    private void initializeHeader(String userToRate) {
        TextView ratingHeader = findViewById(R.id.give_rate_header);
        String headerString = userToRate + "'s Rating";
        ratingHeader.setText(headerString);
    }

    private void initializeStatusView() {
        statusView = findViewById(R.id.give_rating_status);
        statusView.setVisibility(View.INVISIBLE);
    }

    private void initializeRatingButton(String receiverEmail, String jobPostingID, RatingBar starRatingBar) {
        Button submitRatingButton = findViewById(R.id.rating_submit_button);
        submitRatingButton.setOnClickListener(view -> {
            float starRating = starRatingBar.getRating();
            Rating rating = new Rating(senderEmail, receiverEmail, starRating, jobPostingID);
            addRatingToFirebase(rating, jobPostingID);
        });
    }

    private void initializeBackButton(String jobPostingID, String page) {
        Button backButton = findViewById(R.id.give_rating_back_button);
        backButton.setOnClickListener(view -> {
            Intent jobDetailsIntent;
            if (page.equals(Constants.ACCEPT_DECLINE)) {
                jobDetailsIntent = new Intent(GiveRatingsActivity.this, AcceptDeclineTasks.class);
            } else {
                jobDetailsIntent = new Intent(GiveRatingsActivity.this, JobPostingDetailsActivity.class);
                jobDetailsIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPostingID);
            }
            startActivity(jobDetailsIntent);
        });
    }

    /**
     * Add rating from firebase
     * @param rating
     * @param jobPostingID
     */
    protected void addRatingToFirebase(Rating rating, String jobPostingID) {
        DatabaseReference ratingsReference = FirebaseDatabase.getInstance(Constants.FIREBASE_URL)
                .getReference(Rating.class.getSimpleName());
        DAORating daoRating = new DAORating();
        ValueEventListener addRatingEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean jobPostingIDFound = false;
                for (DataSnapshot adSnapShot : dataSnapshot.getChildren()) {
                    jobPostingIDFound = isJobPostingIDFound(jobPostingIDFound, adSnapShot, jobPostingID);
                }
                if (jobPostingIDFound) {
                    // A job can have 2 rating (one from the employee to the employer, the other from the employer to the employee)
                    setRating(rating, daoRating);
                } else {
                    daoRating.add(rating);
                    setStatus("Rating sent successfully.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        };
        ratingsReference.addListenerForSingleValueEvent(addRatingEventListener);
    }

    /**
     * Sets the rating message
     * @param rating
     * @param daoRating
     */
    private void setRating(Rating rating, DAORating daoRating) {
        if (rating.getSenderUserEmail().equals(senderEmail)) {
            setStatus("Error: You have already sent a rating for this job.");
        } else {
            daoRating.add(rating);
            setStatus("Rating sent successfully.");
        }
    }

    /**
     * Ensures that for each job posting only one rating per employee and employer is send.
     * @param jobPostingIDFound
     * @param adSnapShot
     * @param jobPostingID
     * @return
     */
    private boolean isJobPostingIDFound(boolean jobPostingIDFound, DataSnapshot adSnapShot, String jobPostingID) {
        Rating firebaseRating = adSnapShot.getValue(Rating.class);
        if (firebaseRating.getJobPostingID().equals(jobPostingID)) {
            jobPostingIDFound = true;
        }
        return jobPostingIDFound;
    }

    public void setStatus(String statusMessage) {
        statusView.setVisibility(View.VISIBLE);
        statusView.setText(statusMessage);
    }

}
