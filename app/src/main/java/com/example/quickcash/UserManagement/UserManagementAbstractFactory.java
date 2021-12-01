package com.example.quickcash.UserManagement;

import android.content.Context;
import android.content.Intent;

import com.example.quickcash.common.DAO;

public class UserManagementAbstractFactory implements IUserManagementAbstractFactory {

    @Override
    public Intent getIntentInstance(Context context, Class<?> c) {
        return new Intent(context, c);
    }

    @Override
    public IUser getUserInstance() {
        return new User();
    }

    @Override
    public IUser getUserInstanceWithParameters(String firstName, String lastName, String email,
                                               String phone, String password,
                                               String confirmPassword, String isEmployee) {
        return new User(firstName, lastName, email, phone, password,
                confirmPassword, isEmployee);
    }

    @Override
    public DAO getUserDAOInstance() {
        return new DAOUser();
    }

    @Override
    public DAO getPreferenceDAOInstance() {
        return new DAOPreference();
    }

    @Override
    public ISessionManager getSessionInstance(Context context) {
        return new SessionManager(context);
    }

    @Override
    public IAESUtils getAESInstance() {
        return new AESUtils();
    }

    @Override
    public IPreference getPreferenceInstance(String employeeEmail, int jobTypeId,
                                             int duration, int wage, String employeeName) {
        return new Preference(employeeEmail, jobTypeId, duration, wage, employeeName);
    }

    @Override
    public ISessionManagerFirebaseUser getISessionManagerFirebaseUserInstance() {
        return new SessionManagerFirebaseUser();
    }
}
