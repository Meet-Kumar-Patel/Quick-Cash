package com.example.quickcash.user_management;

import android.util.Log;

import com.example.quickcash.common.Constants;

/**
 * This class is responsible for implementing the user object.
 */
public class User implements IUser {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String isEmployee; // Values y = yes, n= no.
    private String employeePreferenceID;

    /**
     * User
     * creating new user
     * @param firstName       user firstname
     * @param lastName        user last name
     * @param email           user email
     * @param phone           user phone
     * @param password        user password
     * @param confirmPassword user confirm password
     * @param isEmployee      employee or employer
     * @return user
     */
    public User(String firstName, String lastName, String email, String phone, String password,
                String confirmPassword, String isEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isEmployee = isEmployee;
    }

    public User() {
    }

    @Override
    public String getEmployeePreferenceID() {
        if (isEmployee.equals("y")) {
            return employeePreferenceID;
        } else {
            Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, "The user is not an employer");
            return null;
        }
    }

    @Override
    public void setEmployeePreferenceID(String employeePreferenceID) {
        if (isEmployee.equals("y")) {
            this.employeePreferenceID = employeePreferenceID;
        } else {
            Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, "The user is not an employer");
        }
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String getIsEmployee() {
        return isEmployee;
    }

    @Override
    public void setIsEmployee(String isEmployee) {
        this.isEmployee = isEmployee;
    }

}
