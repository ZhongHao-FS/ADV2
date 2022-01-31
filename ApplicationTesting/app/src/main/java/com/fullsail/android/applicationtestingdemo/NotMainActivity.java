package com.fullsail.android.applicationtestingdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.android.applicationtestingdemo.fragment.SimpleFragment;

public class NotMainActivity extends AppCompatActivity
        implements SimpleFragment.OnActionButtonClickedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);

            SimpleFragment fragment = SimpleFragment.newInstance(text);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, SimpleFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onActionButtonClicked() {
        Intent result = new Intent();
        result.putExtra(Intent.EXTRA_TEXT, "Result");
        setResult(RESULT_OK, result);
        finish();
    }
}
