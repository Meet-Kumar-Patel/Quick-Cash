package com.example.quickcash.Ratings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.JobPosting.JobPostingDetailsActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiveRatingsActivity extends AppCompatActivity {

    private TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);

        Intent giveRatingIntent = getIntent();
        String receiverEmail = giveRatingIntent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        String jobPostingID = giveRatingIntent.getStringExtra("jobPostingID");
        String userToRate = giveRatingIntent.getStringExtra("userToRate");

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String senderEmail = sessionManager.getKeyEmail();

        RatingBar starRatingBar = (RatingBar) findViewById(R.id.star_rating_bar);

        statusView = (TextView) findViewById(R.id.give_rating_status);
        statusView.setVisibility(View.INVISIBLE);

        Button submitRatingButton = (Button) findViewById(R.id.rating_submit_button);
        Button backButton = (Button) findViewById(R.id.give_rating_back_button);

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float starRating = starRatingBar.getRating();
                Rating rating = new Rating(senderEmail, receiverEmail, starRating, jobPostingID);
                addRatingToFirebase(rating, jobPostingID);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jobDetailsIntent = new Intent(GiveRatingsActivity.this, JobPostingDetailsActivity.class);
                jobDetailsIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPostingID);
                startActivity(jobDetailsIntent);
            }
        });
        //FirebaseDatabase.getInstance().getReference()

    }

    protected void addRatingToFirebase(Rating rating, String jobPostingID) {
        DatabaseReference ratingsReference = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/").getReference(Rating.class.getSimpleName());
        DAORating daoRating = new DAORating();
        ValueEventListener addRatingEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean jobPostingIDFound = false;
                for(DataSnapshot adSnapShot : dataSnapshot.getChildren()) {
                    Rating firebaseRating = adSnapShot.getValue(Rating.class);
                    if(firebaseRating.getJobPostingID().equals(jobPostingID)) {
                        jobPostingIDFound = true;
                    }
                }
                if(jobPostingIDFound) {
                    setStatus("Error: You have already sent a rating for this job.");
                } else {
                    daoRating.add(rating);
                    setStatus("Rating sent successfully.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Could retrieve: " + error.getCode());
            }
        };

        ratingsReference.addListenerForSingleValueEvent(addRatingEventListener);

    }

    public void setStatus(String statusMessage) {
        statusView.setVisibility(View.VISIBLE);
        statusView.setText(statusMessage);
    }

}
