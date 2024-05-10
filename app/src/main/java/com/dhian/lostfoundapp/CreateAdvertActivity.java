package com.dhian.lostfoundapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        Button btnSave = findViewById(R.id.btnSave);
        EditText etName = findViewById(R.id.etName);
        EditText etDate = findViewById(R.id.etDate);
        // Initialize other EditText fields

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                        etDate.setText(selectedDate);
                    }
                });

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields
                String name = etName.getText().toString();
                // Get data from other EditText fields

                // Save data to SQLite database
                // Code to save data to SQLite database goes here

                Toast.makeText(CreateAdvertActivity.this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after saving
            }
        });
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
