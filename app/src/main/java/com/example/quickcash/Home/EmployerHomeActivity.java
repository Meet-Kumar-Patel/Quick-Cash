package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.AcceptDeclineTasks.AcceptDeclineTasks;
import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.Dashboard.EmployerDashboardActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.FirebaseApp;

public class EmployerHomeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "locationOfEmployer";
    // Created intents for navigating to the pages
    protected void navigateToCreateTasksPage() {
//        Intent createTasksPageIntent = new Intent(this, JobPostingTrialActivity.class);
        Intent createTasksPageIntent = new Intent(this, JobPostingActivity.class);
        createTasksPageIntent.putExtra(EXTRA_MESSAGE, "Not Given. Please Enter.");
        startActivity(createTasksPageIntent);
    }

    protected void navigateToDashboardPage() {
        Intent dashboardPageIntent = new Intent(this, EmployerDashboardActivity.class);
        startActivity(dashboardPageIntent);
    }

    protected void navigateToAcceptDecline() {
        Intent acceptDeclineIntent = new Intent(this, AcceptDeclineTasks.class);
        startActivity(acceptDeclineIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //Created 2 buttons: createTasks, dashboard and their respective event listeners to navigate to those pages
        Button createTasks = findViewById(R.id.btnCreateTasksEmployerPage);
        createTasks.setOnClickListener(view ->navigateToCreateTasksPage());

        Button dashboard = findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view -> navigateToDashboardPage());

        Button acceptDecline = findViewById(R.id.acceptdecline);
        acceptDecline.setOnClickListener(view -> navigateToAcceptDecline());

        FirebaseApp.initializeApp(this);
        Button btnLogOut = findViewById(R.id.btnLogOutEmployer);
        btnLogOut.setOnClickListener(view -> sessionManager.logoutUser());
    }
}

