package com.fullsail.android.locationdatademo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final int REQUEST_LOCATION_PERMISSIONS = 0x01001;
    LocationManager mLocationManager;
    boolean mRequestingUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_refresh).setOnClickListener(this);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastKnown = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnown != null) {
                TextView latitude = (TextView) findViewById(R.id.text_latitude);
                latitude.setText(Double.toString(lastKnown.getLatitude()));

                TextView longitude = (TextView) findViewById(R.id.text_longitude);
                longitude.setText(Double.toString(lastKnown.getLongitude()));
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public void onClick(View view) {
        // Check to see if we have permission and that we're not already requesting updates.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mRequestingUpdate) {
            // Request location updates using 'this' as our LocationListener.
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10.0f, this);

            // Track that we're requesting updates so we don't request them twice.
            mRequestingUpdate = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Update the UI with lat/long data from the newly acquired location.
        TextView latitude = (TextView) findViewById(R.id.text_latitude);
        latitude.setText(Double.toString(location.getLatitude()));

        TextView longitude = (TextView) findViewById(R.id.text_longitude);
        longitude.setText(Double.toString(location.getLongitude()));

        // Now that we have a location, let's stop requesting updates.
        if (mRequestingUpdate) {
            mRequestingUpdate = false;
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
