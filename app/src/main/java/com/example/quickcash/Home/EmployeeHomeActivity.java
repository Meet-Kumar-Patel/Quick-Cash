package com.example.quickcash.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.UserManagement.MapsActivity;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.UserManagement.PreferenceActivity;
import com.google.firebase.FirebaseApp;
import com.example.quickcash.Dashboard.EmployeeDashboardActivity;


public class EmployeeHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        //Created 3 buttons: SearchTasks, dashboard, searchByPref and their respective event listeners to navigate to those pages
        Button searchTasks = (Button) findViewById(R.id.btnCreateTasksEmployerPage);
        searchTasks.setOnClickListener(view -> {
            navigateToSearchTasksPage();
        });

        Button dashboard = (Button) findViewById(R.id.btnDashboardEmployerPage);
        dashboard.setOnClickListener(view -> {
            navigateToDashboardPage();
        });

        Button searchByPref = (Button) findViewById(R.id.btnSearchByPrefEmployeePage);
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

