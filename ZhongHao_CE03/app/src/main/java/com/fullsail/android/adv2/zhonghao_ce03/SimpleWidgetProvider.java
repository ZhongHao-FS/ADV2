package com.fullsail.android.adv2.zhonghao_ce03;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class SimpleWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_UPDATE_THEME = "com.fullsail.android.ACTION_UPDATE_THEME";
    public static final String ACTION_UPDATE_LOCATION = "com.fullsail.android.ACTION_UPDATE_LOCATION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUtil.updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            if (action.equals(ACTION_UPDATE_THEME)) {
                boolean darkTheme = prefs.getBoolean("switch_preference_1", false);
                if (darkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }

            if (action.equals(ACTION_UPDATE_LOCATION)) {
                String location = prefs.getString("list_preference_1", "Orlando");
                WidgetUtil.updateWeather(context);
            }
        }

        super.onReceive(context, intent);
    }
}
