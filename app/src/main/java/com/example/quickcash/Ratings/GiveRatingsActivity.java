package com.example.quickcash.Ratings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;

public class GiveRatingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);

        Intent intent = getIntent();
        String receiverEmail = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        String jobPostingID = intent.getStringExtra("jobPostingID");
        String userToRate = intent.getStringExtra("userToRate");

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String senderEmail = sessionManager.getKeyEmail();

        RatingBar starRatingBar = (RatingBar) findViewById(R.id.give_star_rating_bar);

        Button submitRatingButton = (Button) findViewById(R.id.rating_submit_button);

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float starRating = starRatingBar.getRating();
                Rating rating = new Rating(senderEmail, receiverEmail, starRating, jobPostingID);
                DAORating daoRating = new DAORating();
                daoRating.add(rating);
            }
        });

        //FirebaseDatabase.getInstance().getReference()

    }

}
