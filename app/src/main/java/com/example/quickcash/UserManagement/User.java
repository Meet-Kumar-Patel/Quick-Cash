package com.example.quickcash.UserManagement;
import java.util.ArrayList;
import java.util.UUID;

public class User {

    // Initialize Variables
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String isEmployee; // Values y = yes, n= no.
    //ArrayList<String> employeePreferenceIDs = new ArrayList<String>();
    private String employeePreferenceID;



    // Constructors
    public User(String firstName, String lastName, String email, String phone, String password, String confirmPassword, String isEmployee) {
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
    public void setEmployeePreferenceID(String employeePreferenceID){
        if(isEmployee.equals("y")){
            this.employeePreferenceID = employeePreferenceID;
        }
        else{System.out.println("The user is not an Employee");}
    }
    public String getEmployeePreferenceID(){
        if(isEmployee.equals("y")){
            return employeePreferenceID;
        }
        else{System.out.println("The user is not an Employee");
        return null;
        }
    }
    // Getters/Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(String isEmployee) {
        this.isEmployee = isEmployee;
    }

}
