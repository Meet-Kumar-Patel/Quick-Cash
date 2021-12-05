package com.example.quickcash.user_management;

import java.util.HashMap;

public interface ISessionManager {

    void createLoginSession(String email, String password, String name);

    void checkLogin();

    HashMap<String, String> getUserDetails();

    void logoutUser();

    boolean isLoggedIn();

    ISessionManagerFirebaseUser getSessionManagerFirebaseUser();

    String getKeyName();

    String getKeyEmail();
}
