package com.example.quickcash.JobPosting;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.AcceptDeclineTasks.AcceptDeclineTasks;
import com.example.quickcash.Home.EmployerHomeActivity;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.JobTypeStringGetter;
import com.example.quickcash.UserManagement.EmailNotification;
import com.example.quickcash.UserManagement.Preference;
import com.example.quickcash.UserManagement.SessionManager;
import com.example.quickcash.databinding.ActivityJobPostingBinding;
import com.example.quickcash.databinding.ActivityJobPostingBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JobPostingActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private ActivityJobPostingBinding binding;

    // all the instance variables for the method.
    private static final float DEFAULT_ZOOM = 15f;
    private String cityName;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public static final String EXTRA_MESSAGE = "com.example.quickcash.Employer";
    // Variables of the layout
    private EditText jobTitle; // Title of the job posting
    private Spinner jobType; // Job type int value
    private EditText duration; // int number of days
    private EditText location; // String taken from map
    private EditText wage; // Double payment for doing the job
    ArrayList<Preference> preferences = new ArrayList<>();
    private TextView errorMessage; // Message if the creation was successful or not

    // Button
    private Button createJP; // Creates the JP after verification
    private Button homePage; // Sends the user to the homepage

    // Firebase
    private FirebaseDatabase db;

    // Title Arraylist
    private ArrayList<String> titleList = new ArrayList<>();
    private int jobTypeId = 0;
    private String locationStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJobPostingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getLocationPermission();

        //access the intents & show the welcome message
        Intent intent = getIntent();

        // For on Create method only.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    private void remainingStuff(){

        Log.d(TAG, "cityName in remaining stuff:"+cityName);
        locationStr = cityName;
        location = findViewById(R.id.etLocation);
        location.setText(locationStr);

        // Populate dropdown and assign on select listener
        populateSpinner();
        Spinner spinner = (Spinner) findViewById(R.id.etJPType);
        spinner.setOnItemSelectedListener(this);

        // As soon as the activity opens, load the job title => verify that the job title is unique.
        initializeFirebase();
        retrieveDataFromFirebase();

        retrieveAllPreferencesFromFirebase();

        // Declare, Initialize and set on click listener to each btn
        homePage = (Button) findViewById(R.id.btnJPHomePage);
        homePage.setOnClickListener(view -> returnToHomePage());

        createJP = (Button) findViewById(R.id.btnCreateJP);
        createJP.setOnClickListener(view->createNewPosting());


    }

    private void retrieveAllPreferencesFromFirebase() {
        DatabaseReference preferenceReference = db.getReference("Preference");
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    Preference pref = adSnapshot.getValue(Preference.class);

                    preferences.add(pref);
//                    if(pref.getJobType() == jobType) {
//                        int taskPrefType = pref.getJobType();
//                        String taskPref = JobTypeStringGetter.getJobType(taskPrefType);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Maps Stuff
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission : starts");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        // to check if the application has permission to access the fine Location
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // to check if the application has permission to access the fine Location
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // getting a asynchrounous call to the map fragment.
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Google Map is ready", Toast.LENGTH_SHORT).show();
        // check if the map location permission is granted or not.
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    public void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            // to check if the location pe
            if (mLocationPermissionGranted) {
                // to create a task location object by retriving the user's last location.
                Task location = mFusedLocationProviderClient.getLastLocation();
                // to add an complete event listener after the user's last location is retrieved.
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // to check if the task was successful or not.
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            // to check if the current location of the user is null or not.
                            if (currentLocation != null) {
                                try {
                                    // to get call the method to get the current address of the user.
                                    getAddress(currentLocation.getLatitude(),currentLocation.getLongitude());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // move the camera to the user's location.
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM, "current location");

                                // call the other implementation once the city name has been retrieved.
                                remainingStuff();
                            } else
                                Log.d(TAG, "getDeviceLocation: Current location is null");
                        } else {
                            // to show a toast message if the user's location is null.
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                            Toast.makeText(JobPostingActivity.this, "Unable to get curent location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }

    }

    public void moveCamera(LatLng latlng, float zoom, String title) {
        // to move the camera to hte latitude and zoom value.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        // create a marker
        MarkerOptions options = new MarkerOptions().position(latlng).title(title);
        // add the marker
        mMap.addMarker(options);
        //hide the keyboard.
        hideSoftKeyboard();
    }

    private void getAddress(double latitude, double longitude) throws IOException {
        // the Geocoder to make a geographic encoding for the map.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // getting the location for the exact latitude and longitude.
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        // getting the exact city name from the address list.
        cityName = addresses.get(0).getAddressLine(0);
        String[] separated = cityName.split(",");
        cityName = separated[1];
        Log.d(TAG, "cityName in getAddress:"+cityName);

    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initializeFirebase() {
        db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");
    }

    private void retrieveDataFromFirebase() {
        DatabaseReference jpDatabase = FirebaseDatabase.getInstance().getReference(JobPosting.class.getSimpleName());
        jpDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // When the data is received, verify the user credential
                if (dataSnapshot.exists()) {

                    try {
                        getAllTitles(dataSnapshot);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Could retrieve: " + error.getCode());
            }

        });
    }

    protected com.example.quickcash.UserManagement.User getAllTitles(DataSnapshot dataSnapshot) {

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            // get all the titles
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            String title = jobPosting.getJobTitle();
            // Store in a list
            titleList.add(title);
        }
        return null;
    }

    protected void populateSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        jobType = (Spinner) findViewById(R.id.etJPType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    protected String getJobTitle() {
        jobTitle = findViewById(R.id.etJPTitle);
        return jobTitle.getText().toString().trim();
    }

    protected int getDuration() {
        duration = findViewById(R.id.etDuration);
        String durationString = duration.getText().toString();
        if(durationString.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(durationString.trim());

    }

    protected String getLocation() {
        location = findViewById(R.id.etLocation);
        return location.getText().toString().trim();
    }

    protected int getWage() {
        wage = findViewById(R.id.etWage);
        String WageString = wage.getText().toString();
        if(WageString.isEmpty()) return 0;
        return Integer.parseInt(WageString.trim());
    }

    protected void returnToHomePage() {
        Intent intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }

    protected void createNewPosting() {
        //retrieveDataFromFirebase();
        // Get all the fields
        location = findViewById(R.id.etLocation);
        String jobTitle = getJobTitle();
        int duration = getDuration();
        String location = getLocation();
        int wage = getWage();

        // Verify the validity
        if (jobTitle == null || jobTitle.isEmpty()) {
            setStatusMessage("Job title is required.");
        }
        else if (titleList.contains(jobTitle)){
            setStatusMessage("Job Title Already Taken.");
        }
        else if(location.equals("Not Given.PLease Enter.") || location.isEmpty()) {
            setStatusMessage("Please Enter The Location.");
        }
        else if(duration < 1) {
            setStatusMessage("Duration 1 day or longer is required.");
        }
        else if(wage < 15) {
            setStatusMessage("Wage less than $15 are not accepted.");
        }
        else {
            // Reassign location if there is a value. Else the locationStr remains the passed intent value.
            if(!getLocation().isEmpty()) {locationStr = getLocation();}
            // Initializing Session Manager to automatically assign created by and created by Name fields.
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String employer = sessionManager.getKeyEmail();
            String employerName = sessionManager.getKeyName();

            // Declaring and initializing a new Job Posting obj
            JobPosting jobPosting = new JobPosting(jobTitle, jobTypeId, duration, locationStr, wage, employer, employerName);

            // Created Job Posting Database Connection
            DAOJobPosting daoJobPosting = new DAOJobPosting();
            // Adding the Job Posting obj to the database.
            daoJobPosting.add(jobPosting);

            notifyAllEmployee(jobPosting.getJobType());

            switchToJPDetails(jobPosting.getJobPostingId());

        }
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etErrorJP);
        etError.setText(statusMessage);
    }

    protected void switchToJPDetails(String jobTypeId) {
        Intent testIntent = new Intent(this, JobPostingDetailsActivity.class);
        testIntent.putExtra(EXTRA_MESSAGE, jobTypeId);
        startActivity(testIntent);
    }

    // Referred to: https://developer.android.com/guide/topics/ui/controls/spinner
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //Message spinnerItem = (Message) parent.getItemAtPosition(pos);

        switch (pos) {
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

    public void notifyAllEmployee(int jobType){

        for(Preference pref : preferences){
            if(pref.getJobType() == jobType){
                EmailNotification emailNotification = new EmailNotification();
                String employeeEmail = pref.getemployeeEmail() ;
                emailNotification.sendEmailNotification("noreplycsci3130@gmail.com",employeeEmail,"Joben@1999","Hi "+ pref.getemployeeName() +", There is a job posting matching your preference. Please login to check out details");

            }
        }

    }

}