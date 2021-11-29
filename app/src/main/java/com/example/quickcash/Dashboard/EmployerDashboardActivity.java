package com.example.quickcash.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.TaskListRecyclerAdapter;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployerDashboardActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    TaskListRecyclerAdapter adapter;
    RecyclerView recyclerView;
    SearchView jobSearchView;
    ArrayList<JobPosting> jobsCreatedArray = new ArrayList<>();
    DashboardFirebaseTasks dashboardFirebaseTasks = new DashboardFirebaseTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        dashboardFirebaseTasks.getDashboardJobs(this, sessionManager.getKeyEmail());
        recyclerView = findViewById(R.id.recyclerview);
        jobSearchView = findViewById(R.id.jobsearch);
        jobSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        jobSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        Button resetButton = findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobSearchView.setQuery("", false);
                jobSearchView.clearFocus();
            }
        });
        Button homeButton = findViewById(R.id.backToEmployerHomeBtn);
        homeButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(EmployerDashboardActivity.this, EmployerHomeActivity.class);
            startActivity(homeIntent);
        });
    }

    public void setAdapter(TaskListRecyclerAdapter adapter) {
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
