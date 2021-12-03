package com.example.quickcash.AcceptDeclineTasks;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.EmailNotification;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineTasks extends AppCompatActivity {

    AcceptDeclineRecyclerAdapter adapter;
    ArrayList<User> userArrayList = new ArrayList<User>();
    HashMap<String,JobPosting> jobPostingHashMap = new HashMap<>();
    ArrayList<AcceptDeclineObject> acceptDeclineOBJList = new ArrayList<>();
    AcceptDeclineFirebaseTasks acceptDeclineFirebaseTasks = new AcceptDeclineFirebaseTasks();
    String employerEmail;
    Button backToEmployerHomeBtn;
    private RecyclerView recyclerView;

    //Refactoring needed, move search, and button init to new methods.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_accept_decline_tasks);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        employerEmail = sessionManager.getKeyEmail();
        recyclerView = findViewById(R.id.recyclerview);
        acceptDeclineFirebaseTasks.getJobPostingsFromFirebase(this, employerEmail);
        backToEmployerHomeBtn = findViewById(R.id.backToEmployerHomeBtn);
        backToEmployerHomeBtn.setOnClickListener(view -> BackToHome());

        // For on Create method only.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void setAdapter(AcceptDeclineRecyclerAdapter adapter) {
        this.adapter = adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public AcceptDeclineRecyclerAdapter getAdapter() {
        return adapter;
    }

    public HashMap<String, JobPosting> getJobPostingHashMap() {
        return jobPostingHashMap;
    }

    public ArrayList<String> getAppliedUserEmailsFromHashMap(HashMap<String, JobPosting> hashMap) {
        ArrayList<String> emails = new ArrayList<String>();
        for(JobPosting jp : hashMap.values()) {
            emails.addAll(jp.getLstAppliedBy());
        }
        return emails;
    }

    public void addUserToArray(User user) {
        userArrayList.add(user);
    }

    public void addJobPostingToHashMap(String key, JobPosting jobPosting){
        jobPostingHashMap.put(key, jobPosting);
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    protected void addAcceptDeclineOBJ() {
        for (String key: jobPostingHashMap.keySet()) {
            JobPosting jobPosting = jobPostingHashMap.get(key);

            for (int i = 0; i < userArrayList.size(); i++) {
                User user = userArrayList.get(i);
                if(jobPosting.getLstAppliedBy().contains(user.getEmail())) {
                    String userName = user.getFirstName() + " " + user.getLastName();
                    String userEmail = user.getEmail();
                    String jobID = jobPosting.getJobPostingId();
                    String jobTitle = jobPosting.getJobTitle();
                    AcceptDeclineObject acceptDeclineObject = new AcceptDeclineObject(userName, userEmail, jobID, jobTitle, key, !jobPosting.getAccepted().isEmpty());


                    // Once a candidate is accepted we will only the accepted candidate for rating.
                    if(jobPosting.getAccepted().isEmpty()) {
                        acceptDeclineOBJList.add(acceptDeclineObject);

                    } else {
                        if(jobPosting.getAccepted().equals(userEmail)) {
                            acceptDeclineOBJList.add(acceptDeclineObject);

                        }
                    }
                }
            }
        }
    }

    protected JobPosting getJobPosting(String key) {
        JobPosting jobPosting = jobPostingHashMap.get(key);
        return jobPosting;
    }

    public ArrayList<AcceptDeclineObject> getAcceptDeclineOBJList() {
        return acceptDeclineOBJList;
    }

    public void setAcceptDeclineOBJList(ArrayList<AcceptDeclineObject> acceptDeclineOBJList) {
        this.acceptDeclineOBJList = acceptDeclineOBJList;
    }

    public void BackToHome() {
        Intent intent;
        intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }


}