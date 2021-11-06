package com.example.quickcash.TaskList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    ArrayList<String> titlesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerView = findViewById(R.id.recyclerview);

        setJobs();
        setAdapter();
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(titlesArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void setJobs() {
        titlesArrayList.add("Mow Lawn");
        titlesArrayList.add("Plow Snow");
        titlesArrayList.add("Walk Dog");
        titlesArrayList.add("Babysit");
    }

}