package com.dhian.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView tvItemName = findViewById(R.id.tvItemName);
        // Initialize other TextViews for item details
        Button btnDelete = findViewById(R.id.btnDelete);

        // Get item details from Intent extras
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        // Get other item details from Intent extras

        tvItemName.setText(itemName);
        // Set other item details in TextViews

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to delete item from SQLite database goes here

                Toast.makeText(ItemDetailActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after deleting
            }
        });
    }
}
