package com.example.quickcash.Ratings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.FirebaseDatabase;

public class RatingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        Intent intent = getIntent();
        String receiverEmail = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String senderEmail = sessionManager.getKeyEmail();

        Rating rating = new Rating( senderEmail, receiverEmail, 3);

        //FirebaseDatabase.getInstance().getReference()
        DAORating daoRating = new DAORating();
        daoRating.add(rating);
    }

}
