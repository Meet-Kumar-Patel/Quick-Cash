package com.example.quickcash.TaskList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    ArrayList<String> titlesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://csci3130-quickcash-group9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerView = findViewById(R.id.recyclerview);

        getJobPostingsFromFirebase();

    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(titlesArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void setJobTitle(String name) {
        titlesArrayList.add(name);
    }

    private void getJobPostingsFromFirebase() {
        DatabaseReference jobPostingReference = db.getReference("JobPosting");;
        jobPostingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot adSnapshot: dataSnapshot.getChildren()){
                    JobPosting jp = adSnapshot.getValue(JobPosting.class);
                    setJobTitle(jp.getJobTitle());
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });

    }

}