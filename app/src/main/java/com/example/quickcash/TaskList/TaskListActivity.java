package com.example.quickcash.TaskList;

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

import com.example.quickcash.Home.EmployeeHomeActivity;
import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    RecyclerAdapter adapter;
    ArrayList<JobPosting> jobPostingArrayList = new ArrayList<JobPosting>();
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    String city;
    FirebaseTasks firebaseTasks = new FirebaseTasks();

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
        firebaseTasks.getJobPostingsFromFirebase(this, city);
        SearchView jobSearchView = findViewById(R.id.jobsearch);
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
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change this to switch between employee and employer.
                Intent homeIntent = new Intent(TaskListActivity.this, EmployeeHomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    public void setAdapter(RecyclerAdapter adapter) {
        this.adapter = adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public RecyclerAdapter getAdapter() {
        return adapter;
    }

    public void addJobPostingToArray(JobPosting jobPosting) {
        jobPostingArrayList.add(jobPosting);
    }

    public ArrayList<JobPosting> getJobPostingArrayList() {
        return jobPostingArrayList;
    }
}