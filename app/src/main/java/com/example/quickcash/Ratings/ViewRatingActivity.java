package com.example.quickcash.Ratings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ViewRatingActivity extends AppCompatActivity {

    Rating rating = null;
    TextView ratingHeader = null;
    TextView starRatingNumber = null;
    RatingBar ratingBar = null;
    Button backButton;
    float ratingSum = 0;
    String userToRate = "";
    int numRatings = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);
        Intent intent = getIntent();
        String receiverEmail = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        String jobPostingID = intent.getStringExtra("jobPostingID");
        userToRate = intent.getStringExtra("userToRate");
        String page = intent.getStringExtra("page");
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String senderEmail = sessionManager.getKeyEmail();
        retrieveDataFromFirebase(receiverEmail);
        initializeBackButton(jobPostingID, page);
    }

    private void initializeBackButton(String jobPostingID, String page) {
        backButton = findViewById(R.id.rating_back_button);
        backButton.setOnClickListener(view -> {
            Intent jobDetailsIntent;
            if (page.equals("acceptDecline")) {
                jobDetailsIntent = new Intent(ViewRatingActivity.this,
                        AcceptDeclineTasks.class);
            } else {
                jobDetailsIntent = new Intent(ViewRatingActivity.this,
                        JobPostingDetailsActivity.class);
                jobDetailsIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPostingID);
            }
            startActivity(jobDetailsIntent);
        });
    }

    protected void retrieveDataFromFirebase(String receiverEmail) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_URL)
                .getReference(Rating.class.getSimpleName());
        jpDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {
                    try {
                        getRatingByID(dataSnapshot, receiverEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    protected Rating getRatingByID(DataSnapshot dataSnapshot, String receiverEmail) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Rating ratingInFirebase = snapshot.getValue(Rating.class);
            boolean receiverEmailMatches = ratingInFirebase.getReceiverUserEmail()
                    .equals(receiverEmail);
            if (receiverEmailMatches) {
                ratingSum += ratingInFirebase.getRatingValue();
                numRatings++;
            }
        }
        populateLayout(userToRate, numRatings, ratingSum);
        return null;
    }

    public void populateLayout(String userToRate, int numReviews, float reviewSum) {
        findLayout();
        float ratingVal = calculateRating(numReviews, reviewSum);
        if (numReviews == 0) {
            ratingVal = 0;
        }
        setRatingInfo(userToRate, ratingVal);
    }

    private void setRatingInfo(String userToRate, float ratingVal) {
        String headerString = userToRate + "'s Rating";
        ratingHeader.setText(headerString);
        String ratingNumber = ratingVal + "/5";
        starRatingNumber.setText(ratingNumber);
        ratingBar.setRating(ratingVal);
    }

    public float calculateRating(int numReviews, float reviewSum) {
        return reviewSum / numReviews;
    }

    public void findLayout() {
        ratingHeader = findViewById(R.id.rating_header);
        starRatingNumber = findViewById(R.id.star_rating_number);
        ratingBar = findViewById(R.id.star_rating_bar);
        ratingBar.setClickable(false);
        ratingBar.setEnabled(false);
    }

}
