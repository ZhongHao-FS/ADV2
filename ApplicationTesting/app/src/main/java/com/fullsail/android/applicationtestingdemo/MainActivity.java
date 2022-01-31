package com.fullsail.android.applicationtestingdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.android.applicationtestingdemo.fragment.SimpleFragment;

public class MainActivity extends AppCompatActivity
        implements SimpleFragment.OnActionButtonClickedListener {
    private static final int REQUEST_NOT_MAIN_ACTIVITY = 0x01001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            SimpleFragment fragment = SimpleFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, SimpleFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onActionButtonClicked() {
        Intent notMainIntent = new Intent(this, NotMainActivity.class);
        notMainIntent.putExtra(Intent.EXTRA_TEXT, "Started");
        startActivityForResult(notMainIntent, REQUEST_NOT_MAIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_NOT_MAIN_ACTIVITY && resultCode == RESULT_OK) {
            SimpleFragment fragment = (SimpleFragment)getSupportFragmentManager()
                    .findFragmentByTag(SimpleFragment.TAG);
            if(fragment != null && data != null && data.hasExtra(Intent.EXTRA_TEXT)) {
                fragment.setDisplayString(data.getStringExtra(Intent.EXTRA_TEXT));
            }
        }
    }
}
