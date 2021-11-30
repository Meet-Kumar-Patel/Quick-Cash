package com.example.quickcash.JobPosting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

import com.example.quickcash.Ratings.ViewRatingActivity;
import com.example.quickcash.Ratings.GiveRatingsActivity;
import com.example.quickcash.TaskList.TaskListActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobPostingDetailsActivity extends AppCompatActivity {

    // Text views
    private TextView jobTitle;
    private TextView jobType;
    private TextView duration;
    private TextView location;
    private TextView wage;
    private TextView employer;
    private TextView status;

    // Btns
    private Button btnApply;
    private Button btnSearchMore;
    private Button btnTaskCompleted;
    private JobPosting jobPostingOBJ;

    // Logged user info
    private String userEmail = "";
    private String snapshotKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);

        // Ensure that the user is logged in
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        // Get user email from Session Manager
        userEmail = sessionManager.getKeyEmail();

        //access the intents & show the welcome message
        Intent intent = getIntent();
        //Received location from map and show to the user
        String jobID = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);

        // get the JP
        retrieveDataFromFirebase(jobID);

        // Add functionality to the btns
        btnApply = (Button) findViewById(R.id.btnJPDApplyNow);
        btnApply.setOnClickListener(view -> addApplicant(userEmail));
        // Setting Default visibility to invisible. If the applicant is not the employer => the btn will become visible.
        btnApply.setVisibility(View.INVISIBLE);

        btnSearchMore = (Button) findViewById(R.id.btnJPDReturnToSearch);
        btnSearchMore.setOnClickListener(view -> returnToSearch());

        btnTaskCompleted = (Button) findViewById(R.id.btnJPDMarkCompleted);
        btnTaskCompleted.setOnClickListener(view -> markCompleted());
        btnTaskCompleted.setVisibility(View.INVISIBLE);
    }


    protected void retrieveDataFromFirebase(String id) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/").getReference(JobPosting.class.getSimpleName());
        jpDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {

                    try {
                        getJPbyID(dataSnapshot, id);
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

    protected JobPosting getJPbyID(DataSnapshot dataSnapshot, String id) throws Exception {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            boolean idMatches = jobPosting.getJobPostingId().equals(id);
            if (idMatches) {
                populateLayout(jobPosting);
                jobPostingOBJ = jobPosting;
                snapshotKey = snapshot.getKey();
                return jobPosting;
            }
        }
        return null;
    }

    protected void populateLayout(JobPosting jobPosting) {
        // Get the layout views
        findLayouts();

        // Set values
        fillLayouts(jobPosting);

        // if the employer accesses the app then he will not see the btn
        if(jobPosting.getCreatedBy().equals(userEmail)) {
            restrictEmployerActions();
        }
        else {
            showCorrectStatusBtn(jobPosting);
        }

    }

    public void showCorrectStatusBtn(JobPosting jobPosting) {
        // If the user has already applied to the job => show applied.
        btnApply.setText("Apply Now");
        btnApply.setClickable(false);
        
        if(jobPosting.getLstAppliedBy().size() > 0) {
            // If the user has applied and 
            if(jobPosting.getLstAppliedBy().contains(userEmail)) {
                
                if(jobPosting.getAccepted().isEmpty()) {
                    btnApply.setText("Applied");
                } else {
                    if (jobPosting.getAccepted().equals(userEmail)) {
                        btnApply.setText("Accepted");
                        if(!jobPosting.isTaskComplete()) {
                            btnTaskCompleted.setVisibility(View.VISIBLE);
                        }
                    } else {
                        btnApply.setText("Sorry Rejected");
                    }
                }
            }
            // User has not applied
            else if (!jobPosting.getAccepted().isEmpty()) {
                btnApply.setText("Candidate Already Selected");
            }
            
        }
        else {
            btnApply.setClickable(true);
        }
        btnApply.setVisibility(View.VISIBLE);
    }

    protected void findLayouts() {
        jobTitle = (TextView) findViewById(R.id.txtInvoice);
        jobType = (TextView) findViewById(R.id.txtJPDTypeValue);
        duration = (TextView) findViewById(R.id.txtJPDDurationValue);
        location = (TextView) findViewById(R.id.txtJDPLocationValue);
        wage = (TextView) findViewById(R.id.txtJPDWageValue);
        employer = (TextView) findViewById(R.id.txtJDPCreatedByValue);
        status = (TextView) findViewById(R.id.txtJPDStatusValue);
        btnTaskCompleted = (Button) findViewById(R.id.btnJPDMarkCompleted);
    }

    protected void fillLayouts(JobPosting jobPosting) {
        jobTitle.setText(jobPosting.getJobTitle());
        jobType.setText(convertJPType(jobPosting.getJobType()));
        duration.setText(jobPosting.getDuration() + " ");
        location.setText(jobPosting.getLocation());
        wage.setText(jobPosting.getWage() + " ");
        employer.setText(jobPosting.getCreatedByName());
        changeCompletedStatus(jobPosting);
        allowToCheckRating();
    }

    private void changeCompletedStatus(JobPosting jobPosting) {
        if(jobPosting.isTaskComplete()) {
            status.setText("Task Completed");
        }
        else {
            status.setText("Task Not Completed");
        }
    }

    public void allowToCheckRating() {
        employer.setClickable(true);
        employer.setOnClickListener(view -> openRateEmployer());
        TextView employerDesc =findViewById(R.id.txtJDPCreatedBy);
        employerDesc.setText("Employer (Click to give rating)");
    }

    public void openRateEmployer() {
        Intent rateIntent;
        if(jobPostingOBJ.isTaskComplete()){
            if(jobPostingOBJ.getAccepted().equals(userEmail)) {
                // Only the accepted employee can rate the employer
                rateIntent = new Intent(this, GiveRatingsActivity.class);
            }
            else {
                rateIntent = new Intent(this, ViewRatingActivity.class);
            }
        } else {
            rateIntent = new Intent(this, ViewRatingActivity.class);
        }

        rateIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPostingOBJ.getCreatedBy());
        rateIntent.putExtra("jobPostingID", jobPostingOBJ.getJobPostingId());
        rateIntent.putExtra("userToRate", jobPostingOBJ.getCreatedByName());
        rateIntent.putExtra("page", "jobPostingDetails");
        startActivity(rateIntent);
    }

    public void restrictEmployerActions() {
        btnApply.setVisibility(View.INVISIBLE);
        btnTaskCompleted.setVisibility(View.INVISIBLE);
    }

    //can be refactored
    protected String convertJPType(int id) {
        String type = "";
        switch (id) {
            case 1:
                type = "Mowing The Lawn";
                break;
            case 2:
                type = "Design Website";
                break;
            case 3:
                type = "Walking A Dog";
                break;
            case 4:
                type = "Hourly Babysitting";
                break;
            case 5:
                type = "Picking Up Grocery";
                break;
            default:
                type = "Repairing Computer";
        }
        return type;
    }

    protected void returnToSearch() {
        // Needs to be changed to search page
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }

    protected void markCompleted() {
        // Needs to be changed to search page
        jobPostingOBJ.setTaskComplete(true);
        changeCompletedStatus(jobPostingOBJ);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        daoJobPosting.update(jobPostingOBJ, snapshotKey);

    }

    protected void addApplicant(String userEmail) {

        ArrayList arrayList = jobPostingOBJ.getLstAppliedBy();
        if (arrayList.size() == 0) {
            arrayList = new ArrayList();
            arrayList.add(userEmail);
        } else if (!arrayList.contains(userEmail)) {
            arrayList.add(userEmail);
        }

        jobPostingOBJ.setLstAppliedBy(arrayList);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        daoJobPosting.update(jobPostingOBJ, snapshotKey);

        // Show confirmation message
        setStatusMessage("Your application was send successfully!");

        // Change btn text
        btnApply.setText("Applied");
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.txtJPDError);
        etError.setText(statusMessage);
    }

}
