package com.example.quickcash.UserManagement;

public class LogInActivity {

    protected boolean isUsernameEmpty(String username){
            return username.trim().isEmpty();
    }

    protected boolean isPasswordEmpty(String password){
        return password.trim().isEmpty();
    }

}
