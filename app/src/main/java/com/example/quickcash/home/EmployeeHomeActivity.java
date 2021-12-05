package com.example.quickcash.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.dashboard.EmployeeDashboardActivity;
import com.example.quickcash.user_management.MapsActivity;
import com.example.quickcash.user_management.PreferenceActivity;
import com.example.quickcash.user_management.SessionManager;
import com.google.firebase.FirebaseApp;

/**
 * Creates a page which allows the an employee to access the different pages of the app.
 */
public class EmployeeHomeActivity extends AppCompatActivity {

    /**
     * Initialized the page on creation.
     *
     * @param savedInstanceState
     */
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
        employeeHeader.setText("Welcome to Home, " + sessionManager.getKeyName() + ".");
    }

    /**
     * Creates and accesses an intent to switch to the task searching activity.
     */
    protected void navigateToSearchTasksPage() {
        Intent searchTasksPageIntent = new Intent(this, MapsActivity.class);
        startActivity(searchTasksPageIntent);
    }

    /**
     * Creates and accesses an intent to switch to the dashboard activity.
     */
    protected void navigateToDashboardPage() {
        Intent dashboardPageIntent = new Intent(this, EmployeeDashboardActivity.class);
        startActivity(dashboardPageIntent);
    }

    /**
     * Creates and accesses an intent to switch to the preference page activity.
     */
    protected void navigateToSearchPrefPage() {
        Intent searchTasksPageIntent = new Intent(this, PreferenceActivity.class);
        startActivity(searchTasksPageIntent);
    }


}

