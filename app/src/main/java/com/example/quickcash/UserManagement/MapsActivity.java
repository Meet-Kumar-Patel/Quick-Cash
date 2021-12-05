package com.example.quickcash.UserManagement;
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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.quickcash.JobPosting.JobPostingActivity;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.TaskList.TaskListActivity;
import com.example.quickcash.databinding.ActivityMapsBinding;
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
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This method is responsible for implementing the Map Activity features.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // all the instance variables for the method.
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String cityName;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    // boolean variable to check whether it is an employee or an employer.
    private boolean isEmployee = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getLocationPermission();

        // referencing all the buttons in variables.
        Button searchByPreference = (Button) findViewById(R.id.Search_Preference);
        Button searchByManual = (Button) findViewById(R.id.search_Manual);
        Button createTask = (Button) findViewById(R.id.create_Tasks);

        // to check if is the employee or the employer.
        if(!isEmployee){
            searchByPreference.setVisibility(View.INVISIBLE);
            searchByManual.setVisibility(View.INVISIBLE);
            createTask.setVisibility(View.VISIBLE);
        }
        else{
            searchByPreference.setVisibility(View.VISIBLE);
            searchByManual.setVisibility(View.VISIBLE);
            createTask.setVisibility(View.INVISIBLE);
        }
        // setting up an on click listener for the search by preference button.
        searchByPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switching the user to Search By Preference Form.
                // -- Please change this from MainActivity to the Search By preference form activity.
                Intent intent = new Intent(getApplicationContext(),TaskListActivity.class);
                Log.d(TAG,"city name:"+cityName);
                // --- You need to pass the cityName as a extra attribute to the search preference form.
                Toast.makeText(MapsActivity.this, cityName, Toast.LENGTH_LONG).show();
               intent.putExtra("City",cityName);
               intent.putExtra("Preference", true);
                startActivity(intent);
            }
        });
        // setting up an on click listener for the search by manual button.
        searchByManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switching the user to Search By Manual Form.
                // -- Please change this from MainActivity to the Search By Manual form activity.
                Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
                Toast.makeText(MapsActivity.this, "", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        // setting up an on click listener for the search by create Task button for the employer.
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switching the user to Create Task Activity.
                // -- Please change this from MainActivity to the Create Task activity and comment the Toast.
                Intent intent = new Intent(getApplicationContext(),JobPostingActivity.class);
                Toast.makeText(MapsActivity.this, "create Task clicked", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    /**
     * This method is responsible for getting the location permission from the user if not already provided.
     */
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

    /**
     * This method is responsible for requesting the permission
     * @param requestCode the passed in request codes.
     * @param permissions the requested permissions
     * @param grantResults the grant results for the requested permissions
     */
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

    /**
     * This method is responsible for initialising the map for the application.
     */
    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // getting a asynchrounous call to the map fragment.
        mapFragment.getMapAsync(this);
    }

    /**
     * This method was taken from the course TA's provided repository.
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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

    /**
     * This method was also taken from the course TA's provided repository.
     * This method is used to retrieve the device location dynamically.
     */
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
                                // just print a null log
                            } else
                                Log.d(TAG, "getDeviceLocation: Current location is null");
                        } else {
                            // to show a toast message if the user's location is null.
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                            Toast.makeText(MapsActivity.this, "Unable to get curent location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }

    }

    /**
     * This method is responsible for the camera motion
     * @param latlng the latitude of the location
     * @param zoom the zoom of the location
     * @param title the title of the location.
     */
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

    /**
     * This method is used to retrieve the city name from the location
     * @param latitude the latitude of the user location
     * @param longitude the longitude of the user location
     * @throws IOException The Input Output Exception which might occur while finding the user location.
     */
    private void getAddress(double latitude, double longitude) throws IOException {
        // the Geocoder to make a geographic encoding for the map.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // getting the location for the exact latitude and longitude.
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        // getting the exact city name from the address list.
        cityName = addresses.get(0).getAddressLine(0);
        String[] separated = cityName.split(",");
        cityName = separated[2];
    }

    /**
     * This method is used to hide the onscreen keyboard.
     */
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}