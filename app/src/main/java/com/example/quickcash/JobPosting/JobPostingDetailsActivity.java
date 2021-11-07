package com.example.quickcash.JobPosting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
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

    private JobPosting jobPostingOBJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_details);

        // Ensure that the user is logged in
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //access the intents & show the welcome message
        Intent intent = getIntent();

        //Received location from map and show to the user
        String jobID = intent.getStringExtra(JobPostingActivity.EXTRA_MESSAGE);

        // get the JP
        retrieveDataFromFirebase(jobID);

        // Add functionality to the btns
        btnApply = (Button) findViewById(R.id.btnJPDApplyNow);
        btnApply.setOnClickListener(view -> addApplicant());

        btnSearchMore = (Button) findViewById(R.id.btnJPDReturnToSearch);
        btnSearchMore.setOnClickListener(view -> returnToSearch());

        // Job Posting Status: Apply now (Btn) (add name to lstApplied), Pending (txt), Accepted or Rejected
        // Apply Now should change to Applied (disabled) when the user has applied or hide.

    }

    private void retrieveDataFromFirebase(String id) {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance().getReference(JobPosting.class.getSimpleName());
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
                return jobPosting;
            }
        }
        return null;
    }

    protected void populateLayout(JobPosting jobPosting) {
        // Get the layout views
        jobTitle = (TextView) findViewById(R.id.txtJobTitle);
        jobType = (TextView) findViewById(R.id.txtJPDTypeValue);
        duration = (TextView) findViewById(R.id.txtJPDDurationValue);
        location = (TextView) findViewById(R.id.txtJDPLocationValue);
        wage = (TextView) findViewById(R.id.txtJPDWageValue);
        employer = (TextView) findViewById(R.id.txtJDPCreatedByValue);
        status = (TextView) findViewById(R.id.txtJPDStatusValue);

        // Set values
        jobTitle.setText(jobPosting.getJobTitle());
        jobType.setText(convertJPType(jobPosting.getJobType()));
        duration.setText(jobPosting.getDuration() + " ");
        location.setText(jobPosting.getLocation());
        wage.setText(jobPosting.getWage() + " ");
        employer.setText(jobPosting.getCreatedByName());
    }

    protected String convertJPType(int id) {
        String type = "";
        switch (id) {
            case 0:
                type = "Repairing Computer";
                break;
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
        Intent intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }

    protected void addApplicant() {
        // Get user email from Session Manager
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String userEmail = sessionManager.getKeyEmail();
        ArrayList arrayList = jobPostingOBJ.getLstAppliedBy();
        if (arrayList == null) {
            arrayList = new ArrayList();
            arrayList.add(userEmail);
        } else if (!arrayList.contains(userEmail)) {
            arrayList.add(userEmail);
        }

        jobPostingOBJ.setLstAppliedBy(arrayList);
        DAOJobPosting daoJobPosting = new DAOJobPosting();
        daoJobPosting.add(jobPostingOBJ);

        // Show confirmation message
        setStatusMessage("Your application was send successfully!");

    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.txtJPDError);
        etError.setText(statusMessage);
    }
}
