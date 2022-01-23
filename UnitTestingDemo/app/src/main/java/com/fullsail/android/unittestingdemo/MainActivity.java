package com.fullsail.android.unittestingdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.android.unittestingdemo.fragment.PersonListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            PersonListFragment fragment = PersonListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, PersonListFragment.TAG)
                    .commit();
        }
    }
}
