package com.fullsail.android.adv2.zhonghao_ce01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity implements DetailFragment.DeleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() != null) {
            String title = getIntent().getStringExtra(getString(R.string.putExtraTitle_key));
            String description = getIntent().getStringExtra(getString(R.string.putExtraDescript_key));
            String imagePath = getIntent().getStringExtra(getString(R.string.putExtraFilePath_key));

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.detail_fragment_container, DetailFragment.newInstance(title, description, imagePath)).commit();
        }
    }

    @Override
    public void onDeleteMarker() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}