package com.dhian.lostfoundapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> taskList;
    Context context;
    private ItemController itemController;

    public void setTaskList(List<Item> itemList) {
        this.taskList = itemList;
    }

    public ItemAdapter(List<Item> items, Context context) {
        this.taskList = items;
        this.context = context;
        itemController = new ItemController(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemTask = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lost_found, viewGroup, false);
        return new MyViewHolder(itemTask);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int i) {
        Item task = taskList.get(i);

        String name = task.getName();
        String postType = task.getPost_type();
        String location = task.getLocation();
        String itemDescription = postType + ": " + name + " in " + location;
        myViewHolder.description.setText(String.valueOf(itemDescription));
    }

    private void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description;

        MyViewHolder(View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.tvDescription);
        }
    }
}

