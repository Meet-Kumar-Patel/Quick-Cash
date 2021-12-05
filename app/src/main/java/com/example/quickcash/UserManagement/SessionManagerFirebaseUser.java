package com.example.quickcash.UserManagement;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.common.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SessionManagerFirebaseUser implements ISessionManagerFirebaseUser {

    FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_URL);
    private IUser loggedInUser;

    @Override
    public IUser getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(String userEmail) {
        DatabaseReference jobPostingReference = db.getReference("User");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    IUser user = adSnapshot.getValue(User.class);
                    if (user.getEmail().equals(userEmail)) {
                        loggedInUser = user;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.println(Log.WARN, Constants.TAG_ERROR_FIREBASE, error.toString());
            }
        });
    }
}
