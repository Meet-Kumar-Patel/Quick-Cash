package com.example.quickcash.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.RecyclerAdapter;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployerDashboardActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<JobPosting> jobsCreatedArray = new ArrayList<>();
    DashboardFirebaseTasks dashboardFirebaseTasks = new DashboardFirebaseTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        dashboardFirebaseTasks.getDashboardJobs(this, sessionManager.getKeyEmail());
    }

    public void setAdapter(RecyclerAdapter adapter) {
        this.adapter = adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void addJobToArray(JobPosting jobPosting) {
        jobsCreatedArray.add(jobPosting);
    }

    public ArrayList<JobPosting> getJobsCreatedArray() {
        return jobsCreatedArray;
    }
}
