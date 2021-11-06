package com.example.quickcash.TaskList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<String> jobTitlesArrayList;

    public RecyclerAdapter(ArrayList<String> titles) {
        jobTitlesArrayList = titles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = jobTitlesArrayList.get(position);
        holder.nameText.setText(title);
    }

    @Override
    public int getItemCount() {
        return jobTitlesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.titletext);
        }
    }
}
