package com.example.quickcash.UserManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.WelcomePage;
import com.google.firebase.database.FirebaseDatabase;

public class preferencePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static FirebaseDatabase db;
    private Spinner jobType;
    private EditText duration;
    private EditText wage;
    private Button submitB;
    private Button cancelB;
    private int jobTypeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_page);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        addJobsToSpinner();
        Spinner spinner = (Spinner) findViewById(R.id.jType);
        spinner.setOnItemSelectedListener(this);
        initializeFirebase();
        cancelB = (Button) findViewById(R.id.button_Cancel);
        cancelB.setOnClickListener(view -> returnToWelcomePage());
        submitB = (Button) findViewById(R.id.button_Submit);
        submitB.setOnClickListener(view -> createNewPreference());
        duration = (EditText) findViewById(R.id.editTextDuration);
        wage = (EditText) findViewById(R.id.editWage);
    }

    private void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    // ref : https://stackoverflow.com/questions/2505207/how-to-add-item-to-spinners-arrayadapter
    // and https://developer.android.com/guide/topics/resources/string-resource
    protected void addJobsToSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        jobType = (Spinner) findViewById(R.id.jType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    protected int getDuration() {
        duration = findViewById(R.id.editTextDuration);
        return Integer.parseInt(duration.getText().toString().trim());
    }

    protected int getWage() {
        wage = findViewById(R.id.editWage);
        return Integer.parseInt(wage.getText().toString().trim());
    }

    protected void returnToWelcomePage() {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public boolean isEmptyDuration(String duration) {
        return duration.length() <= 0;
    }

    public boolean isEmptyWage(String wage) {
        return wage.length() <= 0;
    }

    public boolean isDurationLessThanone(int duration) {
        return duration < 1;
    }

    public boolean isWageLessThan15(int wage) {
        return wage < 15;
    }

    protected void createNewPreference() {
        int duration = getDuration();
        int wage = getWage();

        if (isDurationLessThanone(duration)) {
            setStatusMessage("Duration of a task must be greator than one day");
        } else if (isWageLessThan15(wage)) {
            setStatusMessage("Wage must be greator or equal $15.");
        } else {
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String employeeName = sessionManager.getKeyName();
            Preference preference = new Preference(jobTypeId, duration, wage, employeeName);
            DPPreference dbPreference = new DPPreference();
            dbPreference.add(preference);
        }
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etErrorJP);
        etError.setText(statusMessage);
    }


    // Referred to: https://developer.android.com/guide/topics/ui/controls/spinner#java

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0:
                jobTypeId = 0;
                break;
            case 1:
                jobTypeId = 1;
                break;
            case 2:
                jobTypeId = 2;
                break;
            case 3:
                jobTypeId = 3;
                break;
            case 4:
                jobTypeId = 4;
                break;
            case 5:
                jobTypeId = 5;
                break;
            default:
                jobTypeId = 0;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback. (Default=0)
    }
}
