package com.example.quickcash.TaskList;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class TaskRecyclerViewHolder extends RecyclerView.ViewHolder {
    Button viewJobButton;
    private final TextView nameText;
    private final TextView locationText;
    private final TextView jobTypeText;

    public TaskRecyclerViewHolder(final View view) {
        super(view);
        nameText = view.findViewById(R.id.employeename);
        locationText = view.findViewById(R.id.location);
        jobTypeText = view.findViewById(R.id.jobtype);
        viewJobButton = view.findViewById(R.id.viewjobbutton);
    }

    public Button getViewJobButton() {
        return viewJobButton;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getLocationText() {
        return locationText;
    }

    public TextView getJobTypeText() {
        return jobTypeText;
    }

}
