package com.example.quickcash.UserManagement;

public class LogInActivityTest {

    // All the fields must be filled
        // If the username is empty then the user must be told to fill it.
        // Id the password is empty then the user should prompt to fill it.
        // If the password and username are empty => user should be prompt to fill them.

    // Check if the user name exist in the firebase
        // if the username E in the firebase => check if the password is valid and login
        // if the username does not E in the firebase => notify "Username/password invalid".

    // Compare the password
        // if the password is stored in the database => decrypt stored stored password
        // If the entered password is same as the decrypted value => login
        // If the entered password is not the same as the decrypted value => notify "Invalid username/ password invalid".


}
