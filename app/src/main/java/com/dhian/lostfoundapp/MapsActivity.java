package com.dhian.lostfoundapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ItemController itemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        itemController = new ItemController(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Item> items = itemController.getItems();

        for (Item item : items) {
            LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(item.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        if (!items.isEmpty()) {
            LatLng firstLocation = new LatLng(items.get(0).getLatitude(), items.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
        }
    }
}
