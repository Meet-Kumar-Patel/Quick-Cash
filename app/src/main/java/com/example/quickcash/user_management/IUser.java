package com.example.quickcash.user_management;

/**
 * This interface is responsible for implementing the IUser class
 */
public interface IUser {

    String getEmployeePreferenceID();

    void setEmployeePreferenceID(String employeePreferenceID);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    String getPassword();

    void setPassword(String password);

    String getConfirmPassword();

    void setConfirmPassword(String confirmPassword);

    String getIsEmployee();

    void setIsEmployee(String isEmployee);
}
