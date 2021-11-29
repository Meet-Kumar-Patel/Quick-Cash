package com.example.quickcash.AcceptDeclineTasks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class AcceptDeclineTasks extends AppCompatActivity {

    AcceptDeclineRecyclerAdapter adapter;
    ArrayList<User> userArrayList = new ArrayList<User>();
    HashMap<String,JobPosting> jobPostingHashMap = new HashMap<>();
    AcceptDeclineFirebaseTasks acceptDeclineFirebaseTasks = new AcceptDeclineFirebaseTasks();
    String employerEmail;

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

   public JobPosting getJobPosting (User user) {
        String email;
        email = user.getEmail();
        for (JobPosting jb : jobPostingHashMap.values()){
            jb.getLstAppliedBy().contains(email);
            if( jb.getLstAppliedBy().contains(email){

            }
        }


}
}