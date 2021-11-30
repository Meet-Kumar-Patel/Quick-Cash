package com.example.quickcash.UserManagement;

import android.content.Context;
import android.content.Intent;

import com.example.quickcash.common.DAO;

public interface IUserManagementAbstractFactory {

    Intent getIntentInstance(Context context, Class<?> c);

    IUser getUserInstance();

    IUser getUserInstanceWithParameters(String firstName, String lastName, String email,
                                        String phone, String password,
                                        String confirmPassword, String isEmployee);

    DAO getUserDAOInstance();

    DAO getPreferenceDAOInstance();

    ISessionManager getSessionInstance(Context context);

    IAESUtils getAESInstance();

    IPreference getPreferenceInstance(String employeeEmail, int jobTypeId,
                                      int duration, int wage, String employeeName);

    ISessionManagerFirebaseUser getISessionManagerFirebaseUserInstance();
}
