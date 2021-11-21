package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.EmployerDashboardActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.FirebaseApp;

public class EmployerHomeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "locationOfEmployer";
    private Button createTasks;
    private Button dashboard;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //Created 2 buttons: createTasks, dashboard and their respective event listeners to navigate to those pages
        createTasks = (Button) findViewById(R.id.btnCreateTasksEmployerPage);
        createTasks.setOnClickListener(view -> {
            navigateToCreateTasksPage();
        });

        dashboard = (Button) findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view -> {
            navigateToDashboardPage();
        });

        FirebaseApp.initializeApp(this);

        Button btnLogOut = findViewById(R.id.btnLogOutEmployer);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

    }
}

