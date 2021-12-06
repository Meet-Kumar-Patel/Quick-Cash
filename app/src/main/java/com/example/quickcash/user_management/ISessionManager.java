package com.example.quickcash.user_management;

import java.util.HashMap;

/**
 * This interface is responsible for implementing the ISessionManager class
 */
public interface ISessionManager {

    /**
     * to create a login sesssion
     * @param email    user email
     * @param password user password
     * @param name     user name
     */
    void createLoginSession(String email, String password, String name);

    /**
     * to  check login credentials of user.
     */
    void checkLogin();

    /**
     * get user details
     * @return user details.
     */
    HashMap<String, String> getUserDetails();

    /**
     * to log out a user
     */
    void logoutUser();

    /**
     * to check if user is logged in or not.
     * @return true if logged in, otherwise false.
     */
    boolean isLoggedIn();

    /**
     * getting the user from firebase.
     * @return sessionManagerFirebaseUser object from firebase.
     */
    ISessionManagerFirebaseUser getSessionManagerFirebaseUser();

    /**
     * getting key name
     * @return key name
     */
    String getKeyName();

    /**
     * getting key email
     * @return key email
     */
    String getKeyEmail();
}
