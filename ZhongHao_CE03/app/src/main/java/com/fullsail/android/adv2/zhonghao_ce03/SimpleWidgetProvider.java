package com.fullsail.android.adv2.zhonghao_ce03;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SimpleWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_CONFIG_PREF = "com.fullsail.android.ACTION_CONFIG_PREF";
    public static final String ACTION_FORECAST = "com.fullsail.android.ACTION_FORECAST";
    public static AppWidgetManager mAppWidgetManager;
    public static int[] mAppWidgetIds;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mAppWidgetManager = appWidgetManager;
        mAppWidgetIds = appWidgetIds;

        updateWeather(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            if (action.equals(ACTION_CONFIG_PREF)) {
                boolean darkTheme = prefs.getBoolean("switch_preference_1", false);
                if (darkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                updateWeather(context);
            }

            if (action.equals(ACTION_FORECAST)) {
                updateWeather(context);
            }
        }

        super.onReceive(context, intent);
    }

    private void updateWeather(Context context) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class).setConstraints(constraints).build();
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}
