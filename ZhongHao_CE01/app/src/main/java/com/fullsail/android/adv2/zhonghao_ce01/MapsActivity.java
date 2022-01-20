package com.fullsail.android.adv2.zhonghao_ce01;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements GoogleMapFragment.SendLocationListener, GoogleMapFragment.MarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            GoogleMapFragment frag = GoogleMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag, GoogleMapFragment.TAG).commit();
        }
    }

    @Override
    public void onSendLocation(LatLng location) {
        Intent explicitForm = new Intent(this, FormActivity.class);
        explicitForm.putExtra(getString(R.string.putExtraLatLng_key), location);
        mLauncher.launch(explicitForm);
    }

    @Override
    public void onMarkerWindowClicked(Marker marker) {
        Intent explicitDetail = new Intent(this, DetailActivity.class);

        explicitDetail.putExtra(getString(R.string.putExtraTitle_key), marker.getTitle());
        explicitDetail.putExtra(getString(R.string.putExtraDescript_key), marker.getSnippet());
        explicitDetail.putExtra(getString(R.string.putExtraFilePath_key), Objects.requireNonNull(marker.getTag()).toString());

        mLauncher.launch(explicitDetail);
    }

    final ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    GoogleMapFragment frag = GoogleMapFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag, GoogleMapFragment.TAG).commit();
                }
            }
    );
}