package com.example.quickcash.JobPosting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.TaskListActivity;
import com.example.quickcash.UserManagement.LogInActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class JobPostingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE = "com.example.quickcash.Employer";
    // Variables of the layout
    private EditText jobTitle; // Title of the job posting
    private Spinner jobType; // Job type int value
    private EditText duration; // int number of days
    private EditText location; // String taken from map
    private EditText wage; // Double payment for doing the job
    private TextView errorMessage; // Message if the creation was successful or not

    // Button
    private Button createJP; // Creates the JP after verification
    private Button homePage; // Sends the user to the homepage

    // Firebase
    private FirebaseDatabase db;

    // Title Arraylist
    private ArrayList<String> titleList = new ArrayList<>();
    private int jobTypeId = 0;
    private String locationStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        // Ensure that the user is logged in
        //SessionManager sessionManager = new SessionManager(getApplicationContext());
        //sessionManager.checkLogin();

        //access the intents & show the welcome message
        Intent intent = getIntent();

        //Received location from map and show to the user
        locationStr = intent.getStringExtra(EmployerHomeActivity.EXTRA_MESSAGE).toString();
        location = findViewById(R.id.etLocation);
        location.setText(locationStr);

        // Populate dropdown and assign on select listener
        populateSpinner();
        Spinner spinner = (Spinner) findViewById(R.id.etJPType);
        spinner.setOnItemSelectedListener(this);

        // As soon as the activity opens, load the job title => verify that the job title is unique.
        initializeFirebase();
        retrieveDataFromFirebase();

        // Declare, Initialize and set on click listener to each btn
        homePage = (Button) findViewById(R.id.btnJPHomePage);
        homePage.setOnClickListener(view -> returnToHomePage());

        createJP = (Button) findViewById(R.id.btnCreateJP);
        createJP.setOnClickListener(view->createNewPosting());
    }

    private void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    private void retrieveDataFromFirebase() {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance().getReference(JobPosting.class.getSimpleName());
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
                System.out.println("Could retrieve: " + error.getCode());
            }

        });
    }

    protected User getAllTitles(DataSnapshot dataSnapshot) {

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
        jobType = (Spinner) findViewById(R.id.etJPType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    protected String getJobTitle() {
        jobTitle = findViewById(R.id.etJPTitle);
        return jobTitle.getText().toString().trim();
    }

    protected int getDuration() {
        duration = findViewById(R.id.etDuration);
        return Integer.parseInt(duration.getText().toString().trim());
    }

    protected String getLocation() {
        location = findViewById(R.id.etLocation);
        return location.getText().toString().trim();
    }

    protected int getWage() {
        wage = findViewById(R.id.etWage);
        return Integer.parseInt(wage.getText().toString().trim());
    }

    protected void returnToHomePage() {
        Intent intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }

    protected void createNewPosting() {
        //retrieveDataFromFirebase();
        // Get all the fields
        location = findViewById(R.id.etLocation);
        String jobTitle = getJobTitle();
        int duration = getDuration();
        String location = getLocation();
        int wage = getWage();

        // Verify the validity
        if (jobTitle == null || jobTitle.isEmpty()) {
            setStatusMessage("Job title is required.");
        }
        else if (titleList.contains(jobTitle)){
            setStatusMessage("Job Title Already Taken.");
        }
        else if(duration < 1) {
            setStatusMessage("Duration 1 day or longer is required.");
        }
        else if(wage < 15) {
            setStatusMessage("Wage less than $15 are not accepted.");
        }
        else {
            // Reassign location if there is a value. Else the locationStr remains the passed intent value.
            if(!getLocation().isEmpty()) {locationStr = getLocation();}
            // Initializing Session Manager to automatically assign created by and created by Name fields.
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String employer = sessionManager.getKeyEmail();
            String employerName = sessionManager.getKeyName();

            // Declaring and initializing a new Job Posting obj
            JobPosting jobPosting = new JobPosting(jobTitle, jobTypeId, duration, locationStr, wage, employer, employerName);

            // Created Job Posting Database Connection
            DAOJobPosting daoJobPosting = new DAOJobPosting();
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

    // Referred to: https://developer.android.com/guide/topics/ui/controls/spinner

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //Message spinnerItem = (Message) parent.getItemAtPosition(pos);

        switch (pos) {
            case 0:
                jobTypeId = 0;
                break;
            case 1:
                jobTypeId = 1;
                break;
            case 2:
                jobTypeId = 2;
                break;
            case 3:
                jobTypeId = 3;
                break;
            case 4:
                jobTypeId = 4;
                break;
            case 5:
                jobTypeId = 5;
                break;
            default:
                jobTypeId = 0;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback. (Default=0)
    }
}
