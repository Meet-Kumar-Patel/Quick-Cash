package com.example.quickcash.job_posting;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.dashboard.EmployerDashboardActivity;
import com.example.quickcash.R;
import com.example.quickcash.ratings.GiveRatingsActivity;
import com.example.quickcash.ratings.ViewRatingActivity;
import com.example.quickcash.user_management.EmailNotification;
import com.example.quickcash.user_management.ISessionManagerFirebaseUser;
import com.example.quickcash.user_management.IUser;
import com.example.quickcash.user_management.MapsActivity;
import com.example.quickcash.user_management.SessionManager;
import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobPostingDetailsActivity extends AppCompatActivity {
    public static final String APPLY_NOW = "Apply Now";
    private TextView jobTitle;
    private TextView jobType;
    private TextView duration;
    private TextView location;
    private TextView wage;
    private TextView employer;
    private TextView status;
    private Button btnApply;
    private Button btnTaskCompleted;
    private JobPosting jobPostingOBJ;
    private String userEmail = "";
    private String snapshotKey;
    private String employerEmail = "";
    private IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);
        // Ensure that the user is logged in
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        // Get user email from Session Manager
        userEmail = sessionManager.getKeyEmail();
        ISessionManagerFirebaseUser sessionManagerFirebaseUser = sessionManager.getSessionManagerFirebaseUser();
        user = sessionManagerFirebaseUser.getLoggedInUser();
        // Access the intents & show the welcome message
        Intent intent = getIntent();
        // Received location from map and show to the user
        String jobID = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);
        // Get the JP
        retrieveDataFromFirebase(jobID);
        initializeButtons();
    }

    private void initializeButtons() {
        // Add functionality to the btns
        btnApply = findViewById(R.id.btnJPDApplyNow);
        btnApply.setOnClickListener(view -> addApplicant(userEmail));
        // Setting Default visibility to invisible.
        // If the applicant is not the employer => the btn will become visible.
        btnApply.setVisibility(View.INVISIBLE);
        Button btnSearchMore = findViewById(R.id.btnJPDReturnToSearch);
        btnSearchMore.setOnClickListener(view -> returnToSearch());
        btnTaskCompleted = findViewById(R.id.btnJPDMarkCompleted);
        btnTaskCompleted.setOnClickListener(view -> markCompleted());
        btnTaskCompleted.setVisibility(View.INVISIBLE);

        // For on Create method only.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    protected void retrieveDataFromFirebase(String id) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_URL)
                .getReference(JobPosting.class.getSimpleName());
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
                Log.e(Constants.FIREBASE_ERROR, Constants.FIREBASE_ERROR + error.getCode());
            }
        });
    }

    /**
     * Finds the job posting with the given id
     * @param dataSnapshot
     * @param id
     * @return Job posting with the given email
     */
    protected JobPosting getJPbyID(DataSnapshot dataSnapshot, String id) {
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
        manageButtons(jobPosting);
    }

    private void manageButtons(JobPosting jobPosting) {
        if (jobPosting.getCreatedBy().equals(userEmail)) {
            restrictEmployerActions();
        } else {
            showCorrectStatusBtn(jobPosting);
        }
    }

    public void showCorrectStatusBtn(JobPosting jobPosting) {
        // If the user has already applied to the job => show applied.
        btnApply.setText(APPLY_NOW);
        btnApply.setClickable(true);
        if (!jobPosting.getLstAppliedBy().isEmpty()) {
            checkStatusOfApplicant(jobPosting);
        } else {
            btnApply.setClickable(true);
        }
        btnApply.setVisibility(View.VISIBLE);
    }

    private void checkStatusOfApplicant(JobPosting jobPosting) {
        // If the user has applied and
        if (jobPosting.getLstAppliedBy().contains(userEmail)) {
            if (jobPosting.getAccepted().isEmpty()) {
                btnApply.setText("Applied");
            } else {
                if (jobPosting.getAccepted().equals(userEmail)) {
                    btnApply.setText("Accepted");
                    if (!jobPosting.isTaskComplete()) {
                        btnTaskCompleted.setVisibility(View.VISIBLE);
                    }
                } else {
                    btnApply.setText("Sorry Rejected");
                }
            }
            btnApply.setClickable(false);
        }
        // User has not applied
        else if (!jobPosting.getAccepted().isEmpty()) {
            btnApply.setText("Candidate Already Selected");
            btnApply.setClickable(false);
        }
    }

    protected void findLayouts() {
        jobTitle = findViewById(R.id.txtJobTitle);
        jobType = findViewById(R.id.txtJPDTypeValue);
        duration = findViewById(R.id.txtJPDDurationValue);
        location = findViewById(R.id.txtJDPLocationValue);
        wage = findViewById(R.id.txtJPDWageValue);
        employer = findViewById(R.id.txtJDPCreatedByValue);
        status = findViewById(R.id.txtJPDStatusValue);
        btnTaskCompleted = findViewById(R.id.btnJPDMarkCompleted);
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
        if (jobPosting.isTaskComplete()) {
            status.setText("Task Completed");
        } else {
            status.setText("Task Not Completed");
        }
    }

    public void allowToCheckRating() {
        employer.setClickable(true);
        employer.setOnClickListener(view -> openRateEmployer());
        TextView employerDesc = findViewById(R.id.txtJDPCreatedBy);
        employerDesc.setText("Employer (Click to give rating)");
    }

    public void openRateEmployer() {
        Intent rateIntent;
        rateIntent = chooseIntent();
        rateIntent.putExtra(JobPostingActivity.EXTRA_MESSAGE, jobPostingOBJ.getCreatedBy());
        rateIntent.putExtra("jobPostingID", jobPostingOBJ.getJobPostingId());
        rateIntent.putExtra("userToRate", jobPostingOBJ.getCreatedByName());
        rateIntent.putExtra("page", "jobPostingDetails");
        startActivity(rateIntent);
    }

    /**
     * Ensures the user access the correct rating activity.
     * @return the correct rate intent
     */
    @NonNull
    private Intent chooseIntent() {
        Intent rateIntent;
        if (jobPostingOBJ.isTaskComplete()) {
            if (jobPostingOBJ.getAccepted().equals(userEmail)) {
                // Only the accepted employee can rate the employer
                rateIntent = new Intent(this, GiveRatingsActivity.class);
            } else {
                rateIntent = new Intent(this, ViewRatingActivity.class);
            }
        } else {
            rateIntent = new Intent(this, ViewRatingActivity.class);
        }
        return rateIntent;
    }

    public void restrictEmployerActions() {
        btnApply.setVisibility(View.INVISIBLE);
        btnTaskCompleted.setVisibility(View.INVISIBLE);
    }

    protected String convertJPType(int id) {
        return JobTypeStringGetter.getJobType(id);
    }

    protected void returnToSearch() {
        Intent intent;
        boolean isEmployee = user.getIsEmployee().equals("y");
        if (isEmployee) {
            intent = new Intent(this, MapsActivity.class);
        } else {
            intent = new Intent(this, EmployerDashboardActivity.class);
        }
        startActivity(intent);
    }

    /**
     * Updates the job posting and sets completed to true.
     */
    protected void markCompleted() {
        jobPostingOBJ.setTaskComplete(true);
        changeCompletedStatus(jobPostingOBJ);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        daoJobPosting.update(jobPostingOBJ, snapshotKey);
        // to notify the employer when the employee marks the task as completed.
        EmailNotification emailNotification = new EmailNotification();
        employerEmail = jobPostingOBJ.getCreatedBy();
        // sender email will be the no reply email
        // the recipients email would be the employer email.
        emailNotification.sendEmailNotification(Constants.EMAIL_ADDRESS,
                employerEmail,  Constants.SENDER_PASSWORD,
                Constants.HI + jobPostingOBJ.getCreatedByName() + "," +
                        " an employee completed the assigned task for your posted job posting." +
                        " Please login to check out details");
    }

    /**
     * Adds the applicant to the database
     * @param userEmail, new applicant
     */
    protected void addApplicant(String userEmail) {
        List<String> lstAppliedBy = addApplicantToLstAppliedBy(userEmail);
        jobPostingOBJ.setLstAppliedBy(lstAppliedBy);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        daoJobPosting.update(jobPostingOBJ, snapshotKey);
        // Show confirmation message
        setStatusMessage("Your application was send successfully!");
        // notify employer when the employee applies.
        EmailNotification emailNotification = new EmailNotification();
        employerEmail = jobPostingOBJ.getCreatedBy();
        // sender email will be the no reply email
        // the recipient email would be the employer email.
        emailNotification.sendEmailNotification(Constants.EMAIL_ADDRESS,
                employerEmail, Constants.SENDER_PASSWORD, Constants.HI+
                        jobPostingOBJ.getCreatedByName() +
                        ", an employee applied for your posted job posting. " +
                        "Please login to check out details");
        // Change btn text
        btnApply.setText("Applied");
    }

    /**
     * Adds the applicant to the lst applicant
     * @param userEmail
     * @return
     */
    @NonNull
    private List<String> addApplicantToLstAppliedBy(String userEmail) {
        List<String> lstAppliedBy = jobPostingOBJ.getLstAppliedBy();
        if (lstAppliedBy.isEmpty()) {
            lstAppliedBy = new ArrayList<>();
            lstAppliedBy.add(userEmail);
        } else if (!lstAppliedBy.contains(userEmail)) {
            lstAppliedBy.add(userEmail);
        }
        return lstAppliedBy;
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.txtJPDError);
        etError.setText(statusMessage);
    }
}
