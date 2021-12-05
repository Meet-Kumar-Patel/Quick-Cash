package com.example.quickcash.TaskList;

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
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.ISessionManagerFirebaseUser;
import com.example.quickcash.UserManagement.IUser;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.common.Constants;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    TaskListRecyclerAdapter adapter;
    ArrayList<JobPosting> jobPostingArrayList = new ArrayList<>();
    String city;
    boolean searchByPreference;
    TaskListFirebaseTasks firebaseTasks = new TaskListFirebaseTasks();
    SessionManager sessionManager;
    SearchView jobSearchView;
    private RecyclerView recyclerView;

    /**
     * Calls methods to initialize TaskListActivity on load.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        initializeJobSearchView();
        initializeResetButton();
        initializeHomeButton();
    }

    private void initializeActivity() {
        Intent intent = getIntent();
        city = intent.getStringExtra(Constants.CITY_INTENT).trim();
        setContentView(R.layout.activity_task_list);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        firebaseTasks.getJobPostingsFromFirebase(this, city);
        searchByPreference = intent.getBooleanExtra(Constants.PREFERENCE_INTENT, false);
        if (searchByPreference) {
            firebaseTasks.getUserPreferenceFromFirebase(this,
                    sessionManager.getKeyEmail());
        }
    }

    private void initializeHomeButton() {
        Button homeButton = findViewById(R.id.backToEmployerHomeBtn);
        homeButton.setOnClickListener(view -> {
            ISessionManagerFirebaseUser sessionManagerFirebaseUser =
                    sessionManager.getSessionManagerFirebaseUser();
            IUser user = sessionManagerFirebaseUser.getLoggedInUser();
            Intent homeIntent;
            if (user.getIsEmployee().equals("y")) {
                homeIntent = new Intent(TaskListActivity.this,
                        EmployeeHomeActivity.class);
            } else {
                homeIntent = new Intent(TaskListActivity.this,
                        EmployerHomeActivity.class);
            }
            startActivity(homeIntent);
        });
    }

    private void initializeResetButton() {
        Button resetButton = findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(view -> {
            jobSearchView.setQuery("", false);
            jobSearchView.clearFocus();
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

    public TaskListRecyclerAdapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the recycler adapter for the task list.
     *
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
     * Adds a job posting to the ArrayList of JobPosting's to be displayed on page.
     *
     * @param jobPosting
     */
    public void addJobPostingToArray(JobPosting jobPosting) {
        jobPostingArrayList.add(jobPosting);
    }

    public List<JobPosting> getJobPostingList() {
        return jobPostingArrayList;
    }

    /**
     * Set's the query to be searched in the search view.
     *
     * @param query
     */
    public void setSearchQuery(String query) {
        jobSearchView.setQuery(query, false);
    }
}