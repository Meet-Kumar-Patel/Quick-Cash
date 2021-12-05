package com.example.quickcash.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.accept_decline_tasks.AcceptDeclineTasks;
import com.example.quickcash.dashboard.EmployerDashboardActivity;
import com.example.quickcash.job_posting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.user_management.SessionManager;
import com.google.firebase.FirebaseApp;

public class EmployerHomeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "locationOfEmployer";

    // Created intents for navigating to the pages
    protected void navigateToCreateTasksPage() {
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
        initializeButtons(sessionManager);
        FirebaseApp.initializeApp(this);
    }

    private void initializeButtons(SessionManager sessionManager) {
        Button createTasks = findViewById(R.id.btnCreateTasksEmployerPage);
        createTasks.setOnClickListener(view -> navigateToCreateTasksPage());
        Button dashboard = findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view -> navigateToDashboardPage());
        Button acceptDecline = findViewById(R.id.acceptdecline);
        acceptDecline.setOnClickListener(view -> navigateToAcceptDecline());
        Button btnLogOut = findViewById(R.id.btnLogOutEmployer);
        btnLogOut.setOnClickListener(view -> sessionManager.logoutUser());
        TextView employeeHeader = findViewById(R.id.etEmployeeMessage);
        employeeHeader.setText("Welcome to Home, "+sessionManager.getKeyName() + ".");
    }
}

