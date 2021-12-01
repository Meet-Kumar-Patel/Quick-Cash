package com.example.quickcash.JobPosting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobPostingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE = Constants.STRING_INTENT_KEY;
    private final ArrayList<String> titleList = new ArrayList<>();
    private final DAOJobPosting daoJobPosting = new DAOJobPosting();
    private EditText location;
    private int jobTypeId = 0;
    private String locationStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        // Ensure that the user is logged in

        //access the intents & show the welcome message
        Intent intent = getIntent();

        //Received location from map and show to the user
        locationStr = intent.getStringExtra(Constants.STRING_INTENT_KEY);
        location = findViewById(R.id.etLocation);
        location.setText(locationStr);

        // Populate dropdown and assign on select listener
        populateSpinner();
        Spinner spinner = findViewById(R.id.etJPType);
        spinner.setOnItemSelectedListener(this);

        // As soon as the activity opens, load the job title => verify that the job title is unique.
        retrieveDataFromFirebase();

        // Declare, Initialize and set on click listener to each btn
        Button homePage = findViewById(R.id.btnJPHomePage);
        homePage.setOnClickListener(view -> returnToHomePage());

        Button createJP = findViewById(R.id.btnCreateJP);
        createJP.setOnClickListener(view -> createNewPosting());
    }

    private void retrieveDataFromFirebase() {
        DatabaseReference jpDatabase = daoJobPosting.getDatabaseReference();
        jpDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {

                    try {
                        getAllTitles(dataSnapshot);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getCode());
            }

        });
    }

    protected com.example.quickcash.UserManagement.User getAllTitles(DataSnapshot dataSnapshot) {

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            // get all the titles
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            String title = jobPosting.getJobTitle();
            // Store in a list
            titleList.add(title);
        }
        return null;
    }

    protected void populateSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        Spinner jobType = findViewById(R.id.etJPType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    protected String getJobTitle() {
        EditText jobTitle = findViewById(R.id.etJPTitle);
        return jobTitle.getText().toString().trim();
    }

    protected int getDuration() {
        EditText duration = findViewById(R.id.etDuration);
        String durationString = duration.getText().toString();
        if (durationString.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(durationString.trim());

    }

    protected String getLocation() {
        location = findViewById(R.id.etLocation);
        return location.getText().toString().trim();
    }

    protected int getWage() {
        EditText wage = findViewById(R.id.etWage);
        String wageString = wage.getText().toString();
        if (wageString.isEmpty()) return 0;
        return Integer.parseInt(wageString.trim());
    }

    protected void returnToHomePage() {
        Intent intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }

    protected void createNewPosting() {
        // Get all the fields
        location = findViewById(R.id.etLocation);
        String jobTitleStr = getJobTitle();
        int durationInt = getDuration();
        locationStr = getLocation();
        int wageInt = getWage();

        // Verify the validity
        if (jobTitleStr == null || jobTitleStr.isEmpty()) {
            setStatusMessage("Job title is required.");
        } else if (titleList.contains(jobTitleStr)) {
            setStatusMessage("Job Title Already Taken.");
        } else if (locationStr.equals("Not Given.PLease Enter.") || locationStr.isEmpty()) {
            setStatusMessage("Please Enter The Location.");
        } else if (durationInt < 1) {
            setStatusMessage("Duration 1 day or longer is required.");
        } else if (wageInt < 15) {
            setStatusMessage("Wage less than $15 are not accepted.");
        } else {
            // Reassign location if there is a value. Else the locationStr remains the passed intent value.
            if (!getLocation().isEmpty()) {
                locationStr = getLocation();
            }
            // Initializing Session Manager to automatically assign created by and created by Name fields.
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String employer = sessionManager.getKeyEmail();
            String employerName = sessionManager.getKeyName();

            // Declaring and initializing a new Job Posting obj
            JobPosting jobPosting = new JobPosting(jobTitleStr, jobTypeId, durationInt, locationStr, wageInt, employer, employerName);

            // Adding the Job Posting obj to the database.
            daoJobPosting.add(jobPosting);

            switchToJPDetails(jobPosting.getJobPostingId());

        }
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etErrorJP);
        etError.setText(statusMessage);
    }

    protected void switchToJPDetails(String jobTypeId) {
        Intent testIntent = new Intent(this, JobPostingDetailsActivity.class);
        testIntent.putExtra(EXTRA_MESSAGE, jobTypeId);
        startActivity(testIntent);
    }

    /**
     * Referred to: https://developer.android.com/guide/topics/ui/controls/spinner
     * The method sets the jobTypeID, if the user does not select job type, default value 0 is
     * given
     */

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        jobTypeId = pos > 0 ? pos : 0;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback. (Default=0)
    }
}
