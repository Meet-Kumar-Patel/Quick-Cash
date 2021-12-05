package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Dashboard.EmployeeDashboardActivity;
import com.example.quickcash.R;
import com.example.quickcash.UserManagement.MapsActivity;
import com.example.quickcash.UserManagement.PreferenceActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.google.firebase.FirebaseApp;


public class EmployeeHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        initializeButtons(sessionManager);
        FirebaseApp.initializeApp(this);
    }

    private void initializeButtons(SessionManager sessionManager) {
        //Created 3 buttons: SearchTasks, dashboard, searchByPref and their respective event listeners to navigate to those pages
        Button searchTasks = findViewById(R.id.btnCreateTasksEmployerPage);
        searchTasks.setOnClickListener(view ->
                navigateToSearchTasksPage()
        );
        Button dashboard = findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view ->
                navigateToDashboardPage()
        );
        Button searchByPref = findViewById(R.id.btnSearchByPrefEmployeePage);
        searchByPref.setOnClickListener(view ->
                navigateToSearchPrefPage()
        );
        Button btnLogOut = findViewById(R.id.btnLogOutEmployee);
        btnLogOut.setOnClickListener(view -> sessionManager.logoutUser());

        TextView employeeHeader = findViewById(R.id.etEmployeeMessage);
        employeeHeader.setText("Welcome to Home, "+sessionManager.getKeyName() + ".");
    }

    // Created intents for navigating to the pages
    protected void navigateToSearchTasksPage() {
        Intent searchTasksPageIntent = new Intent(this, MapsActivity.class);
        startActivity(searchTasksPageIntent);
    }

    protected void navigateToDashboardPage() {
        Intent dashboardPageIntent = new Intent(this, EmployeeDashboardActivity.class);
        startActivity(dashboardPageIntent);
    }

    protected void navigateToSearchPrefPage() {
        Intent searchTasksPageIntent = new Intent(this, PreferenceActivity.class);
        startActivity(searchTasksPageIntent);
    }


}

