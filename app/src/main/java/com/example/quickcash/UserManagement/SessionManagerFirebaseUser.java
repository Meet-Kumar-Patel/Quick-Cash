package com.example.quickcash.UserManagement;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SessionManagerFirebaseUser {

    private User loggedInUser;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");

    public void setLoggedInUser(String userEmail) {
        DatabaseReference jobPostingReference = db.getReference("User");
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    User user = adSnapshot.getValue(User.class);
                    if(user.getEmail().equals(userEmail)) {
                        loggedInUser = user;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
