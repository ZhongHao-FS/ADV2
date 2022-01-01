package com.fullsail.android.googlemapsdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            DemoMapFragment frag = DemoMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag, DemoMapFragment.TAG).commit();
        }
    }
}