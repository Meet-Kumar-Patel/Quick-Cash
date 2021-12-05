package com.example.quickcash.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.TaskListRecyclerAdapter;
import com.example.quickcash.UserManagement.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDashboardActivity extends AppCompatActivity {

    TaskListRecyclerAdapter adapter;
    RecyclerView recyclerView;
    SearchView jobSearchView;
    ArrayList<JobPosting> jobsAppliedForArray = new ArrayList<>();
    DashboardFirebaseTasks dashboardFirebaseTasks = new DashboardFirebaseTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        dashboardFirebaseTasks.getDashboardJobs(this,
                sessionManager.getKeyEmail());
        recyclerView = findViewById(R.id.recyclerview);
        initializeJobSearchView();
        initializeButtons();
    }

    /**
     * Initializes the buttons
     */
    private void initializeButtons() {
        Button resetButton = findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(view -> {
            jobSearchView.setQuery("", false);
            jobSearchView.clearFocus();
        });
        Button homeButton = findViewById(R.id.backToEmployerHomeBtn);
        homeButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(EmployeeDashboardActivity.this,
                    EmployeeHomeActivity.class);
            startActivity(homeIntent);
        });
    }

    private void initializeJobSearchView() {
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
    }

    public void setAdapter(TaskListRecyclerAdapter adapter) {
        this.adapter = adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void addJobToArray(JobPosting jobPosting) {
        jobsAppliedForArray.add(jobPosting);
    }

    public List<JobPosting> getJobsAppliedForArray() {
        return jobsAppliedForArray;
    }
}
