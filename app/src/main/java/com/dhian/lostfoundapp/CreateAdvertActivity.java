package com.dhian.lostfoundapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    private ItemController itemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        Button btnSave = findViewById(R.id.btnSave);
        EditText etName = findViewById(R.id.etName);
        EditText etDate = findViewById(R.id.etDate);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etDescription = findViewById(R.id.etDescription);
        EditText etLocation = findViewById(R.id.etLocation);
        RadioButton rbFound = findViewById(R.id.rbFound);
        RadioButton rbLost = findViewById(R.id.rbLost);

        itemController = new ItemController(CreateAdvertActivity.this);

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

        RadioGroup yourRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        yourRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.rbFound) {
                    System.out.println("FOUND CHECKED");
                } else if (checkedId == R.id.rbLost) {
                    System.out.println("LOST CHECKED");
                }

                System.out.println(group);
                System.out.println(checkedId);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields
                String name = etName.getText().toString();
                String postType = "";
                if (rbFound.isChecked()) {
                    postType = "Found";
                } else if (rbLost.isChecked()) {
                    postType = "Lost";
                }
                String phone = etPhone.getText().toString();
                String description = etDescription.getText().toString();
                String date = etDate.getText().toString();
                String location = etLocation.getText().toString();

                Item newTask = new Item(name, postType, phone, description, date, location );
                long id = itemController.newItem(newTask);
                if (id == -1) {
                    Toast.makeText(CreateAdvertActivity.this, "Error saving. Try again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Item successfully added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
