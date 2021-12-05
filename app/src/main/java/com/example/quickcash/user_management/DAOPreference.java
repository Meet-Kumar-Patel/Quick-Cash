package com.example.quickcash.user_management;

import com.example.quickcash.common.Constants;
import com.example.quickcash.common.DAO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class is responsible for implementing the features of a DAO Preference object.
 */
public class DAOPreference extends DAO {
    private final DatabaseReference databaseReference;

    /**
     * This is the constructor of the class
     */
    public DAOPreference() {
        FirebaseDatabase db = FirebaseDatabase.
                getInstance(Constants.FIREBASE_URL);
        databaseReference = db.getReference(Preference.class.getSimpleName());
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
     * This method is responsible for adding a preference object to a task list.
     * @param preference object which needs to be added to the task list
     * @return a task list object
     */
    @Override
    public Task<Void> add(IPreference preference) {
        return databaseReference.push().setValue(preference);
    }
}
