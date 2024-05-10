package com.dhian.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private ItemController itemController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemController = new ItemController(ItemDetailActivity.this);

        setContentView(R.layout.activity_item_detail);

        TextView tvItemName = findViewById(R.id.tvItemName);
        TextView tvItemPhone = findViewById(R.id.tvItemPhone);
        TextView tvItemDescription = findViewById(R.id.tvItemDescription);
        TextView tvItemLocation = findViewById(R.id.tvItemLocation);
        TextView tvItemDate = findViewById(R.id.tvItemDate);
        TextView tvItemType = findViewById(R.id.tvItemType);


        // Initialize other TextViews for item details
        Button btnDelete = findViewById(R.id.btnDelete);

        // Get item details from Intent extras
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String postType = intent.getStringExtra("post_type");
        String phone = intent.getStringExtra("phone");
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        String date = intent.getStringExtra("date");

        // Set values to text view
        tvItemName.setText(name);
        tvItemPhone.setText(phone);
        tvItemDescription.setText(description);
        tvItemLocation.setText(location);
        tvItemDate.setText(date);
        tvItemType.setText(postType);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("WILL DELETE ITEM");
                System.out.println(id);
                long result = itemController.deleteItem(name);
                System.out.println(result);
                Toast.makeText(ItemDetailActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after deleting
            }
        });
    }
}
