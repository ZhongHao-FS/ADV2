package com.fullsail.android.adv2.zhonghao_ce01;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener, LocationListener {

    public static final String TAG = "GoogleMapFragment.TAG";
    private static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    private static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    private static final String TAG_USER_COMMENT = "UserComment";
    private static final int REQUEST_LOCATION_PERMISSIONS = 0x01001;
    private GoogleMap mMap;
    private LatLng mCurrentLocation;
    private SendLocationListener sListener;
    private MarkerClickListener mListener;
    LocationManager mLocationManager;

    public interface SendLocationListener {
        void onSendLocation(LatLng location);
    }
    public interface MarkerClickListener {
        void onMarkerWindowClicked(Marker marker);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof SendLocationListener) {
            sListener = (SendLocationListener) context;
        }
        if (context instanceof MarkerClickListener) {
            mListener = (MarkerClickListener) context;
        }
    }

    public static GoogleMapFragment newInstance() {
        return new GoogleMapFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getContext() != null) {
            mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastKnown = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnown != null) {
                mCurrentLocation = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
            } else {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 10.0f, this);
            }
        } else if (getActivity() != null) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);
        }

        getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Location lastKnown = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (lastKnown != null) {
                    mCurrentLocation = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
                } else {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 10.0f, this);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        sListener.onSendLocation(mCurrentLocation);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        sListener.onSendLocation(latLng);
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
        mListener.onMarkerWindowClicked(marker);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapLongClickListener(this);

        zoomInCamera();
        loadMapMarkers();
    }

    private void zoomInCamera() {
        if (mMap == null) {
            return;
        }

        if (mCurrentLocation != null) {
            CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 16);
            mMap.animateCamera(cameraMovement);
        }
    }

    private void loadMapMarkers() {
        if (mMap == null) {
            return;
        }

        File[] photos = FileUtility.getImageFiles();
        for (File photoFile: photos) {
            LatLng location = new LatLng(0, 0);
            String title = "";
            String description = "";

            try {
                ExifInterface exif = new ExifInterface(photoFile);
                String delete = exif.getAttribute(TAG_USER_COMMENT);

                if (delete != null && delete.equals("Deleted")) {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),
                            bitmapOptions);
                    OutputStream os = new FileOutputStream(photoFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    continue;
                }
                location = new LatLng(Objects.requireNonNull(exif.getLatLong())[0], exif.getLatLong()[1]);
                title = exif.getAttribute(TAG_IMAGE_UNIQUE_ID);
                description = exif.getAttribute(TAG_IMAGE_DESCRIPTION);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!Objects.equals(title, "") && getContext() != null) {
                MarkerOptions options = new MarkerOptions();
                options.title(title);
                options.snippet(description);
                options.position(location);

                Marker marker = mMap.addMarker(options);
                assert marker != null;

                marker.setTag(photoFile);
            }
        }
    }
}
