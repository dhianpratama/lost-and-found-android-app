package com.dhian.lostfoundapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.Manifest;
import android.location.Location;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateAdvertActivity extends AppCompatActivity {
    private ItemController itemController;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private EditText etLocation;
    private double latitude, longitude;

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }

    // Handler when user get address from current location
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    etLocation.setText(getAddressFromLocation(latitude, longitude));
                } else {
                    // Handle the case where location is null
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        Button btnSave = findViewById(R.id.btnSave);
        EditText etName = findViewById(R.id.etName);
        EditText etDate = findViewById(R.id.etDate);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        RadioButton rbFound = findViewById(R.id.rbFound);
        RadioButton rbLost = findViewById(R.id.rbLost);
        Button btnGetCurrentLocation = findViewById(R.id.btnCurrentLocation);

        Places.initialize(getApplicationContext(), getResources().getString(R.string.GEO_API_KEY));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        itemController = new ItemController(CreateAdvertActivity.this);

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

        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to return
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                // Start the autocomplete intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(CreateAdvertActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
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

                Item newTask = new Item(name, postType, phone, description, date, location, latitude, longitude);
                long id = itemController.newItem(newTask);
                if (id == -1) {
                    Toast.makeText(CreateAdvertActivity.this, "Error saving. Try again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Item successfully added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });
    }

    // Handler when user choose address from the map
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                etLocation.setText(place.getName() + ", " + place.getAddress());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Toast.makeText(this, "Error: " + Autocomplete.getStatusFromIntent(data).getStatusMessage(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation
            }
        }
    }

    // Reverse geocoding to get address by coordinate
    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();

                // Use the address information as needed
                System.out.println("Address: " + addressLine);
                System.out.println("City: " + city);
                System.out.println("State: " + state);
                System.out.println("Country: " + country);
                System.out.println("Postal Code: " + postalCode);

                return addressLine;
            } else {
                System.out.println("No address found for the location.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
