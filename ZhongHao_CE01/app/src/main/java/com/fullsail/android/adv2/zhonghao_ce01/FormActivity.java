package com.fullsail.android.adv2.zhonghao_ce01;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class FormActivity extends AppCompatActivity implements FormFragment.SaveListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if (getIntent() != null) {
            LatLng location = getIntent().getParcelableExtra(getString(R.string.putExtraLatLng_key));

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.form_fragment_container, FormFragment.newInstance(location)).commit();
        }
    }

    @Override
    public void onSaveForm() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
