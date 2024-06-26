package com.example.quickcash.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.home.EmployeeHomeActivity;
import com.example.quickcash.R;
import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * This class is responsible
 */
public class PreferenceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner jobType;
    private EditText duration;
    private EditText wage;
    private int jobTypeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_page);
        initializeActivity();
    }

    private void initializeActivity() {
        Button submitButton;
        Button cancelButton;
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        ISessionManager sessionManager = userManagementAbstractFactory.
                getSessionInstance(getApplicationContext());
        sessionManager.checkLogin();
        addJobsToSpinner();
        Spinner spinner = findViewById(R.id.jType);
        spinner.setOnItemSelectedListener(this);
        cancelButton = findViewById(R.id.button_Cancel);
        cancelButton.setOnClickListener(view -> returnToEmployeePage());
        submitButton = findViewById(R.id.button_Submit);
        submitButton.setOnClickListener(view -> createNewPreference());
        duration = findViewById(R.id.editTextDuration);
        wage = findViewById(R.id.editWage);
    }
    /**
     * adding jobs to the spinner of the screen
     */
    // ref : https://stackoverflow.com/questions/2505207/how-to-add-item-to-spinners-arrayadapter
    // and https://developer.android.com/guide/topics/resources/string-resource
    protected void addJobsToSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.
                createFromResource(this, R.array.planets_array,
                        android.R.layout.simple_spinner_item);
        jobType = findViewById(R.id.jType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    /**
     * @return the duration of job as inputted
     */
    protected int getDuration() {
        duration = findViewById(R.id.editTextDuration);
        return Integer.parseInt(duration.getText().toString().trim());
    }

    /**
     * @return the wage of job as inputted
     */
    protected int getWage() {
        wage = findViewById(R.id.editWage);
        return Integer.parseInt(wage.getText().toString().trim());
    }

    protected void returnToEmployeePage() {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        Intent intent = userManagementAbstractFactory.
                getIntentInstance(this, EmployeeHomeActivity.class);
        startActivity(intent);
    }
    /**
     * @return if the duration is empty or not
     */
    public boolean isEmptyDuration(String duration) {
        return duration.length() <= 0;
    }
    /**
     * @return if the wage empty of not
     */

    public boolean isEmptyWage(String wage) {
        return wage.length() <= 0;
    }

    /**
     * @return if the duration if less than one or not
     */
    public boolean isDurationLessThanOne(int duration) {
        return duration < 1;
    }
    /**
     * @return if the wage if less than 15 or not
     */

    public boolean isWageLessThan15(int wage) {
        return wage < 15;
    }


    protected void createNewPreference() {
        int durationInt = getDuration();
        int wageInt = getWage();
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        if (isDurationLessThanOne(durationInt)) {
            setStatusMessage(Constants.TASK_DURATION_ERROR);
        } else if (isWageLessThan15(wageInt)) {
            setStatusMessage(Constants.WAGE_AMOUNT_ERROR);
        } else {
            ISessionManager sessionManager = userManagementAbstractFactory.
                    getSessionInstance(getApplicationContext());
            String employeeName = sessionManager.getKeyName();
            String employeeEmail = sessionManager.getKeyEmail();
            IPreference preference = userManagementAbstractFactory.
                    getPreferenceInstance(employeeEmail, jobTypeId, durationInt, wageInt,
                            employeeName);
            updatePreferenceFromFirebase(preference);
        }
    }

    private void updatePreferenceFromFirebase(IPreference preference) {
        IUserManagementAbstractFactory userManagementAbstractFactory = UserManagementInjector.
                getInstance().getUserManagementAbstractFactory();
        DAO daoPreference = userManagementAbstractFactory.getPreferenceDAOInstance();
        DatabaseReference preferenceReference = daoPreference.getDatabaseReference();
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                boolean emailFound = false;
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    IPreference pref = adSnapshot.getValue(Preference.class);
                    if (pref.getEmployeeEmail().equals(preference.getEmployeeEmail())) {
                        //Updates values if preference with email exists.
                        String keyID = adSnapshot.getKey();
                        preferenceReference.child(keyID).child("jobType").
                                setValue(preference.getJobType());
                        preferenceReference.child(keyID).child("duration").
                                setValue(preference.getDuration());
                        preferenceReference.child(keyID).child("wage").
                                setValue(preference.getWage());
                        emailFound = true;
                        break;
                    }

                }
                if (!emailFound) {
                    daoPreference.add(preference);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        };
        preferenceReference.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etErrorJP);
        etError.setText(statusMessage);
    }

    // Referred to: https://developer.android.com/guide/topics/ui/controls/spinner#java
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        jobTypeId = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        //Not required
    }
}
