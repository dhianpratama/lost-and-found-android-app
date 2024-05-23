package com.dhian.lostfoundapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> itemList;
    Context context;
    private ItemController itemController;

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public ItemAdapter(List<Item> items, Context context) {
        this.itemList = items;
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
        Item task = itemList.get(i);

        String name = task.getName();
        String postType = task.getPost_type();
        String location = task.getLocation();
        String itemDescription = postType + ": " + name + " in " + location;
        myViewHolder.description.setText(String.valueOf(itemDescription));

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item taskSelected = itemList.get(i);
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("id", taskSelected.getId());
                intent.putExtra("name", taskSelected.getName());
                intent.putExtra("description", taskSelected.getDescription());
                intent.putExtra("phone", taskSelected.getPhone());
                intent.putExtra("post_type", taskSelected.getPost_type());
                intent.putExtra("location", taskSelected.getLocation());
                intent.putExtra("date", taskSelected.getDate());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description;

        MyViewHolder(View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.tvDescription);
        }
    }
}

