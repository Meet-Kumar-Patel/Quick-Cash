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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AcceptDeclineTasks extends AppCompatActivity {

    AcceptDeclineRecyclerAdapter adapter;
    ArrayList<JobPosting> jobPostingArrayList = new ArrayList<JobPosting>();
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    String city;
    AcceptDeclineFirebaseTasks acceptDeclineFirebaseTasks = new AcceptDeclineFirebaseTasks();
    String employerEmail = "jo@test.com";

    private RecyclerView recyclerView;

    //Refactoring needed, move search, and button init to new methods.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        city = intent.getStringExtra("City");
        setContentView(R.layout.activity_task_list);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
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

    public void addJobPostingToArray(JobPosting jobPosting) {
        jobPostingArrayList.add(jobPosting);
    }

    public ArrayList<JobPosting> getJobPostingArrayList() {
        return jobPostingArrayList;
    }
}