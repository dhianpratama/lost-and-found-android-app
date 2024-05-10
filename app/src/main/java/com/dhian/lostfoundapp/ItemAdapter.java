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

        String title = task.getName();
        String description = task.getDescription();
        String dueDate = task.getDate();
        myViewHolder.title.setText(title);
        myViewHolder.description.setText(String.valueOf(description));
        myViewHolder.dueDate.setText("Due date " + dueDate);
        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item taskSelected = taskList.get(i);
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("id", taskSelected.getId());
                intent.putExtra("name", taskSelected.getName());
                intent.putExtra("description", taskSelected.getDescription());
                intent.putExtra("date", taskSelected.getDate());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        myViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Item taskToDelete = taskList.get(i);
                taskList.remove(i);
                itemController.deleteTask(taskToDelete);
                updateView();

            }
        });

    }

    private void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dueDate;
        ImageView ivEdit;
        ImageView ivDelete;

        MyViewHolder(View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.tvDescription);
        }
    }
}

