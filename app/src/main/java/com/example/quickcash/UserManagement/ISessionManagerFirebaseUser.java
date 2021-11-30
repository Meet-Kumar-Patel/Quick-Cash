package com.example.quickcash.UserManagement;

public interface ISessionManagerFirebaseUser {
    IUser getLoggedInUser();

    void setLoggedInUser(String userEmail);
}
