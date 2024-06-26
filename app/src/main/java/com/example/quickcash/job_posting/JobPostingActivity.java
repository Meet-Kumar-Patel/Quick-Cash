package com.example.quickcash.job_posting;

import static android.content.ContentValues.TAG;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcash.R;
import com.example.quickcash.common.Constants;
import com.example.quickcash.databinding.ActivityJobPostingBinding;
import com.example.quickcash.home.EmployerHomeActivity;
import com.example.quickcash.user_management.IPreference;
import com.example.quickcash.user_management.Preference;
import com.example.quickcash.user_management.SessionManager;
import com.example.quickcash.user_management.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

/**
 * Creates a page containing a form to allow an employer to create a job posting.
 */
public class JobPostingActivity extends AppCompatActivity implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE = Constants.STRING_INTENT_KEY;
    // all the instance variables for the method.
    private static final float DEFAULT_ZOOM = 15f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final ArrayList<String> titleList = new ArrayList<>();
    private final DAOJobPosting daoJobPosting = new DAOJobPosting();
    private final ArrayList<IPreference> preferences = new ArrayList<>();
    private EditText location;
    private GoogleMap mMap;
    private ActivityJobPostingBinding binding;
    private String cityName;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private int jobTypeId = 0;
    private String locationStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobPostingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getLocationPermission();
        Intent intent = getIntent();
        locationStr = intent.getStringExtra(Constants.STRING_INTENT_KEY);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /**
     * This method initializes the buttons, calls methods to retrieve data from firebase
     */
    private void initializeLayout() {
        Log.d(TAG, "cityName in remaining stuff:" + cityName);
        locationStr = cityName;
        location = findViewById(R.id.etLocation);
        location.setText(locationStr);

        // Populate dropdown and assign on select listener
        populateSpinner();
        Spinner spinner = findViewById(R.id.etJPType);
        spinner.setOnItemSelectedListener(this);
        retrieveDataFromFirebase();
        retrieveAllPreferencesFromFirebase();
        Button homePage = findViewById(R.id.btnJPHomePage);
        homePage.setOnClickListener(view -> returnToHomePage());
        Button createJP = findViewById(R.id.btnCreateJP);
        createJP.setOnClickListener(view -> createNewPosting());
    }

    /**
     * Populates the preferences list with all the available preferences.
     */
    private void retrieveAllPreferencesFromFirebase() {
        DatabaseReference preferenceReference = FirebaseDatabase.getInstance(Constants.FIREBASE_URL)
                .getReference("Preference");
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    IPreference pref = adSnapshot.getValue(Preference.class);
                    preferences.add(pref);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }

    /**
     * Requests the location permission
     */
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission : starts");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        // to check if the application has permission to access the fine Location
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // to check if the application has permission to access the fine Location
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * Starts the location permission if the location permission is granted.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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

    /**
     * Starts the map fragment
     */
    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // getting a asynchronous call to the map fragment.
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Google Map is ready", Toast.LENGTH_SHORT).show();
        // check if the map location permission is granted or not.
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    /**
     * Gets the location and initializes the layout.
     */
    public void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            // to check if the location pe
            if (mLocationPermissionGranted) {
                // to create a task location object by retriving the user's last location.
                Task location = mFusedLocationProviderClient.getLastLocation();
                // to add an complete event listener after the user's last location is retrieved.
                location.addOnCompleteListener(task -> {
                    // to check if the task was successful or not.
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        // to check if the current location of the user is null or not.
                        if (currentLocation != null) {
                            try {
                                // to get call the method to get the current address of the user.
                                getAddress(currentLocation.getLatitude(),
                                        currentLocation.getLongitude());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // move the camera to the user's location.
                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "current location");

                            // call the other implementation once the city name has been retrieved.
                            initializeLayout();
                        } else
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                    } else {
                        // to show a toast message if the user's location is null.
                        Log.d(TAG, "getDeviceLocation: Current location is null");
                        Toast.makeText(JobPostingActivity.this,
                                "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }

    }

    /**
     * Moves the camera in the maps window to the users location.
     *
     * @param latlng
     * @param zoom
     * @param title
     */
    public void moveCamera(LatLng latlng, float zoom, String title) {
        // to move the camera to the latitude and zoom value.
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
        cityName = separated[2];
        Log.d(TAG, "cityName in getAddress:" + cityName);

    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void retrieveDataFromFirebase() {
        DatabaseReference jpDatabase = daoJobPosting.getDatabaseReference();
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
                Log.e(Constants.FIREBASE_ERROR, Constants.FIREBASE_ERROR + error.getCode());
            }

        });
    }

    /**
     * Gets a list of titles, to make sure that the title is unique
     *
     * @param dataSnapshot
     * @return
     */
    protected User getAllTitles(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            // get all the titles
            JobPosting jobPosting = snapshot.getValue(JobPosting.class);
            String title = jobPosting.getJobTitle();
            // Store in a list
            titleList.add(title);
        }
        return null;
    }

    /**
     * Initializes a spinner object with job types to be selected by employer.
     */
    protected void populateSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        Spinner jobType = findViewById(R.id.etJPType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(arrayAdapter);
    }

    /**
     * Gets the job title that has been entered by the employer.
     *
     * @return Job Title
     */
    protected String getJobTitle() {
        EditText jobTitle = findViewById(R.id.etJPTitle);
        return jobTitle.getText().toString().trim();
    }

    /**
     * Gets the duration that has been entered by the employer.
     *
     * @return Duration in hours.
     */
    protected int getDuration() {
        EditText duration = findViewById(R.id.etDuration);
        String durationString = duration.getText().toString();
        if (durationString.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(durationString.trim());

    }

    /**
     * Gets the location that has been set by the maps.
     *
     * @return
     */
    protected String getLocation() {
        location = findViewById(R.id.etLocation);
        return location.getText().toString().trim();
    }

    /**
     * Fills the location in the layout from the intent
     *
     * @param intent
     */
    private void setLocation(Intent intent) {
        locationStr = intent.getStringExtra(Constants.STRING_INTENT_KEY);
        location = findViewById(R.id.etLocation);
        location.setText(locationStr);
    }

    /**
     * Gets the wage that has been entered by the employer.
     *
     * @return
     */
    protected int getWage() {
        EditText wage = findViewById(R.id.etWage);
        String wageString = wage.getText().toString();
        if (wageString.isEmpty()) return 0;
        return Integer.parseInt(wageString.trim());
    }

    /**
     * Returns to employer homepage
     */
    protected void returnToHomePage() {
        Intent intent = new Intent(this, EmployerHomeActivity.class);
        startActivity(intent);
    }

    /**
     * Gets the input fromt he layout and verifies the fields.
     */
    protected void createNewPosting() {
        location = findViewById(R.id.etLocation);
        String jobTitleStr = getJobTitle();
        int durationInt = getDuration();
        locationStr = getLocation();
        int wageInt = getWage();
        verifyJobPostingIsValid(jobTitleStr, durationInt, wageInt);
    }

    /**
     * If the user input are valid, it notifies the preferred users using
     * the observer and calla createJobPosting. Else it displays an error message.
     *
     * @param jobTitleStr
     * @param durationInt
     * @param wageInt
     */
    private void verifyJobPostingIsValid(String jobTitleStr, int durationInt, int wageInt) {
        if (jobTitleStr == null || jobTitleStr.isEmpty()) {
            setStatusMessage("Job title is required.");
        } else if (titleList.contains(jobTitleStr)) {
            setStatusMessage("Job Title Already Taken.");
        } else if (locationStr.equals("Not Given.PLease Enter.") || locationStr.isEmpty()) {
            setStatusMessage("Please Enter The Location.");
        } else if (durationInt < 1) {
            setStatusMessage("Duration 1 day or longer is required.");
        } else if (wageInt < 15) {
            setStatusMessage("Wage less than $15 are not accepted.");
        } else {
            if (!getLocation().isEmpty()) {
                locationStr = getLocation();
            }
            JobPosting jobPosting = createJobPosting(jobTitleStr, durationInt, wageInt);
            Observer preference = new Preference();
            preference.notifyUsersWithPreferredJobs(jobPosting, preferences);
            switchToJPDetails(jobPosting.getJobPostingId());
        }
    }

    /**
     * Adds the job posting to the database
     *
     * @param jobTitleStr
     * @param durationInt
     * @param wageInt
     * @return
     */
    @NonNull
    private JobPosting createJobPosting(String jobTitleStr, int durationInt, int wageInt) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String employer = sessionManager.getKeyEmail();
        String employerName = sessionManager.getKeyName();
        JobPosting jobPosting = new JobPosting(jobTitleStr, jobTypeId, durationInt, locationStr,
                wageInt, employer, employerName);
        daoJobPosting.add(jobPosting);
        return jobPosting;
    }

    private void setStatusMessage(String statusMessage) {
        TextView etError = findViewById(R.id.etErrorJP);
        etError.setText(statusMessage);
    }

    /**
     * Changes pages to the job posting details once the job has been created.
     *
     * @param jobTypeId
     */
    protected void switchToJPDetails(String jobTypeId) {
        Intent testIntent = new Intent(this, JobPostingDetailsActivity.class);
        testIntent.putExtra(EXTRA_MESSAGE, jobTypeId);
        startActivity(testIntent);
    }

    /**
     * Referred to: https://developer.android.com/guide/topics/ui/controls/spinner
     * The method sets the jobTypeID, if the user does not select job type, default value 0 is
     * given
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        jobTypeId = pos > 0 ? pos : 0;
    }

    /**
     * Required by interface.
     *
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback. (Default=0)
    }
}