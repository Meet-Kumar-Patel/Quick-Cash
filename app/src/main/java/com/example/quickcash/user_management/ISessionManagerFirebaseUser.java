package com.example.quickcash.user_management;

public interface ISessionManagerFirebaseUser {
    IUser getLoggedInUser();

    void setLoggedInUser(String userEmail);
}
