package com.example.quickcash.user_management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.quickcash.WelcomePage;

import java.util.HashMap;

// Taken from: https://stackoverflow.com/questions/23720313/android-save-user-session
public class SessionManager implements ISessionManager {
    // public email name
    public static final String KEY_PASSWORD = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // Shared pref mode
    static final int PRIVATE_MODE = 0;
    // Session file name
    private static final String PREF_NAME = "SessionManagerFile";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "userName";
    private static final ISessionManagerFirebaseUser sessionManagerFirebaseUser =
            UserManagementInjector.getInstance().getUserManagementAbstractFactory().
                    getISessionManagerFirebaseUserInstance();
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context context;

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    @Override
    public void createLoginSession(String email, String password, String name) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing email
        editor.putString(KEY_EMAIL, email);
        // Storing Password
        editor.putString(KEY_PASSWORD, password);
        //Put name
        editor.putString(KEY_NAME, name);
        // commit changes
        editor.commit();
        sessionManagerFirebaseUser.setLoggedInUser(email);
    }

    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    @Override
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LogInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    @Override
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_NAME, null));
        return user;
    }

    /**
     * Clear session details
     */
    @Override
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
        Intent i = new Intent(context, WelcomePage.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring welcomePage
        context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    @Override
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    @Override
    public ISessionManagerFirebaseUser getSessionManagerFirebaseUser() {
        return sessionManagerFirebaseUser;
    }

    @Override
    public String getKeyName() {
        return pref.getString(KEY_NAME, "Not Exist");
    }

    @Override
    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL, "Not Exist");
    }

}
