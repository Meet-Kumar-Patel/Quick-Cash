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

import java.util.ArrayList;

public class AcceptDeclineTasks extends AppCompatActivity {

    AcceptDeclineRecyclerAdapter adapter;
    ArrayList<String> userArrayList = new ArrayList<String>();
    AcceptDeclineFirebaseTasks acceptDeclineFirebaseTasks = new AcceptDeclineFirebaseTasks();
    String employerEmail = "jo@test.com";

    private RecyclerView recyclerView;

    //Refactoring needed, move search, and button init to new methods.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
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

    public void addUserToArray(JobPosting jobPosting) {
        ArrayList<String> user = jobPosting.getLstAppliedBy();
        userArrayList.addAll(user);

    }

    public ArrayList<String> getUserArrayList() {
        return userArrayList;
    }
}