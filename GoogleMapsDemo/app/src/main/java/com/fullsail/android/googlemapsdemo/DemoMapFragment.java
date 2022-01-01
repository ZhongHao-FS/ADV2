package com.fullsail.android.googlemapsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DemoMapFragment extends SupportMapFragment implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = "MapFragment.TAG";

    public static DemoMapFragment newInstance() {
        return new DemoMapFragment();
    }

    private GoogleMap mMap;
    private final double mOfficesLatitude = 28.590647;
    private final double mOfficesLongitude = -81.304510;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setInfoWindowAdapter(DemoMapFragment.this);
            mMap.setOnInfoWindowClickListener(DemoMapFragment.this);

            zoomInCamera();
            addMapMarker();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMapAsync(callback);
    }


    private void zoomInCamera() {
        if (mMap == null) {
            return;
        }

        LatLng officeLocation = new LatLng(mOfficesLatitude, mOfficesLongitude);
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(officeLocation, 16);
        mMap.animateCamera(cameraMovement);
    }

    private void addMapMarker() {
        if (mMap == null) {
            return;
        }

        MarkerOptions options = new MarkerOptions();
        options.title("Full Sail University");
        options.snippet("MDV Offices");

        LatLng officeLocation = new LatLng(mOfficesLatitude, mOfficesLongitude);
        options.position(officeLocation);

        mMap.addMarker(options);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View contents = LayoutInflater.from(getActivity()).inflate(R.layout.info_window, null);

        ((TextView)contents.findViewById(R.id.title)).setText(marker.getTitle());
        ((TextView)contents.findViewById(R.id.snippet)).setText(marker.getSnippet());

        return contents;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        new AlertDialog.Builder(getActivity()).setTitle(marker.getTitle()).setMessage(marker.getSnippet())
                .setIcon(R.mipmap.ic_launcher).setPositiveButton("OK", null).show();
    }
}