package com.example.quickcash.user_management;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * This class is responsible for implementing the features of a DAO User object.
 */
public class DAOUser extends DAO {
    private final DatabaseReference databaseReference;

    /**
     * This is the constructor for the class.
     */
    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.
                getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    /**
     * This method is responsible for getting the database reference from firebase.
     * @return a database reference object from the firebase.
     */
    @Override
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /**
     * This method is responsible for adding a user object to a task list.
     * @param user object which needs to be added to the task list
     * @return a task list object
     */
    @Override
    public Task<Void> add(IUser user) {
        return databaseReference.push().setValue(user);
    }

}
