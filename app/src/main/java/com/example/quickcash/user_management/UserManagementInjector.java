package com.example.quickcash.user_management;

/**
 * This class is responsible for implementing  the UserManagementInjector object.
 */
public class UserManagementInjector {
    private static UserManagementInjector instance = null;
    private final IUserManagementAbstractFactory userManagementAbstractFactory;

    private UserManagementInjector() {
        userManagementAbstractFactory = new UserManagementAbstractFactory();
    }

    public static UserManagementInjector getInstance() {
        if (instance == null) {
            instance = new UserManagementInjector();
        }
        return instance;
    }

    public IUserManagementAbstractFactory getUserManagementAbstractFactory() {
        return userManagementAbstractFactory;
    }
}
