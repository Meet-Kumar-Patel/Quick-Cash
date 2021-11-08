package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.TaskList.TaskListActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.FirebaseApp;

public class EmployeeHomeActivity extends AppCompatActivity {
private Button searchTasks;
private Button dashboard;
private Button searchByPref;

// Created intents for navigating to the pages
    protected void navigateToSearchTasksPage() {
        Intent searchTasksPageIntent = new Intent(this, TaskListActivity.class);
        startActivity(searchTasksPageIntent);
    }

    protected void navigateToDashboardPage() {
        Intent dashboardPageIntent = new Intent(this, TaskListActivity.class);
        startActivity(dashboardPageIntent);
    }

    protected void navigateToSearchPrefPage() {
        Intent searchTasksPageIntent = new Intent(this, TaskListActivity.class);
        startActivity(searchTasksPageIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //Created 3 buttons: SearchTasks, dashboard, searchByPref and their respective event listeners to navigate to those pages
        searchTasks = (Button) findViewById(R.id.btnCreateTasksEmployerPage);
        searchTasks.setOnClickListener(view -> {
            navigateToSearchTasksPage();
        });

        dashboard = (Button) findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view -> {
            navigateToDashboardPage();
        });

        searchByPref = (Button) findViewById(R.id.btnSearchByPrefEmployeePage);
        searchByPref.setOnClickListener(view -> {
            navigateToSearchPrefPage();
        });

        FirebaseApp.initializeApp(this);

        Button btnLogOut = findViewById(R.id.btnLogOutEmployee);

        btnLogOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

    }

}

