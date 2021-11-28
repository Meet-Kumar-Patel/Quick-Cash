package com.example.quickcash.Ratings;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;

public class ViewRatingActivity extends AppCompatActivity {
    Rating rating = null;
    TextView rating_header = null;
    TextView star_rating_number = null;
    RatingBar ratingBar = null;
    String ratingKey = "";

    String userToRate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);

        Intent intent = getIntent();
        String receiverEmail = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        String jobPostingID = intent.getStringExtra("jobPostingID");
        userToRate = intent.getStringExtra("userToRate");

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String senderEmail = sessionManager.getKeyEmail();

        //Rating rating = new Rating( senderEmail, receiverEmail, 3, jobPostingID);
        retrieveDataFromFirebase(receiverEmail, senderEmail, jobPostingID);

    }

    protected void retrieveDataFromFirebase(String receiverEmail, String senderEmail,String jobID) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/").getReference(Rating.class.getSimpleName());
        jpDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {
                    try {
                        getRatingByID(dataSnapshot, receiverEmail, senderEmail, jobID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Could retrieve: " + error.getCode());
            }
        });
    }

    protected Rating getRatingByID(DataSnapshot dataSnapshot, String receiverEmail, String senderEmail, String jobID) throws Exception {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Rating ratingInFirebase = snapshot.getValue(Rating.class);
            boolean receiverEmailMatches = ratingInFirebase.getReceiverUserEmail().equals(receiverEmail);
            boolean senderEmailMatches = ratingInFirebase.getSenderUserEmail().equals(senderEmail);
            boolean jobIDMatches = ratingInFirebase.getJobPostingID().equals(jobID);

            if (receiverEmailMatches && senderEmailMatches && jobIDMatches) {
                rating = ratingInFirebase;
                populateLayout(rating);
                ratingKey = snapshot.getKey();
                return rating;
            }
        }
        return null;
    }

    public void populateLayout(Rating rating) {
        // Find the layout
        findLayout();

        // Fill with rating info
        rating_header.setText( userToRate + "'s Rating");
        star_rating_number.setText(rating.getRatingValue() + "/5");
        ratingBar.setRating(rating.getRatingValue());
    }

    public void findLayout() {
        rating_header = findViewById(R.id.rating_header);
        star_rating_number = findViewById(R.id.star_rating_number);
        ratingBar = findViewById(R.id.star_rating_bar);
        ratingBar.setClickable(false);
    }
}
