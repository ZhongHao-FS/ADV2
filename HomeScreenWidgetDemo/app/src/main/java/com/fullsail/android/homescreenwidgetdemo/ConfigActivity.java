package com.fullsail.android.homescreenwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    private int mWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_layout);

        // Get the starting intent
        Intent starter = getIntent();
        // Get the ID of the widget from extras
        mWidgetId = starter.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        // If the ID is invalid, close the activity to prevent possible crashes with updating an invalid widget.
        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            ConfigFragment fragment = ConfigFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, ConfigFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            // Get a widget manager using the activity as a context.
            AppWidgetManager mgr = AppWidgetManager.getInstance(this);
            // Update the widget.
            WidgetUtil.updateWidget(this, mgr, mWidgetId);

            // Pass back the ID of the configured widget
            Intent result = new Intent();
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
            setResult(RESULT_OK, result);

            // Close the activity.
            finish();
        }

        return true;
    }

}
