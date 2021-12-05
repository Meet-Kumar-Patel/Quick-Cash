package com.example.quickcash.user_management;

/**
 * This interface is responsible for implementing the ISessionManagerFirebaseUser class
 */
public interface ISessionManagerFirebaseUser {
    /**
     * get logged in user
     * @return get logged in user
     */
    IUser getLoggedInUser();

    /**
     * set user email
     * @param userEmail given user email
     */
    void setLoggedInUser(String userEmail);
}
