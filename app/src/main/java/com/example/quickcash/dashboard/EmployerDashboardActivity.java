package com.example.quickcash.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.home.EmployerHomeActivity;
import com.example.quickcash.job_posting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.task_list.TaskListRecyclerAdapter;
import com.example.quickcash.user_management.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an employer dashboard which displays a task list of all the jobs an employer has created.
 */
public class EmployerDashboardActivity extends AppCompatActivity {
    TaskListRecyclerAdapter adapter;
    RecyclerView recyclerView;
    SearchView jobSearchView;
    ArrayList<JobPosting> jobsCreatedArray = new ArrayList<>();
    DashboardFirebaseTasks dashboardFirebaseTasks = new DashboardFirebaseTasks();

    /**
     *Initializes the EmployerDashboardActivity on creation.
     *
     * @param savedInstanceState
     */
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

    private void initializeButtons() {
        Button resetButton = findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(view -> {
            jobSearchView.setQuery("", false);
            jobSearchView.clearFocus();
        });
        Button homeButton = findViewById(R.id.backToEmployerHomeBtn);
        homeButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(EmployerDashboardActivity.this,
                    EmployerHomeActivity.class);
            startActivity(homeIntent);
        });
    }

    /**
     * Initializes the filter for jobs
     */
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

    /**
     * Sets the recycler adapter.
     * @param adapter
     */
    public void setAdapter(TaskListRecyclerAdapter adapter) {
        this.adapter = adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Adds a job posting to the complete array of job postings that the employer has created.
     * @param jobPosting
     */
    public void addJobToArray(JobPosting jobPosting) {
        jobsCreatedArray.add(jobPosting);
    }

    public List<JobPosting> getJobsCreatedArray() {
        return jobsCreatedArray;
    }
}
