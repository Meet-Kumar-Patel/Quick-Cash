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
        setContentView(R.layout.activity_task_list);
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

    public void addUserToArray(HashMap<String,JobPosting> jobPostingHashMap) {
        ArrayList<User> users = new ArrayList<>();
        for(JobPosting jp : jobPostingHashMap.values()){
            users.add()
        }

        userArrayList.addAll(users);
    }

    public void addJobPostingToHashMap(String key, JobPosting jobPosting){
        jobPostingHashMap.put(key, jobPosting);
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void addUserToHashMap(String key,User user) {
        userHashMap.put(key, user);
    }
}