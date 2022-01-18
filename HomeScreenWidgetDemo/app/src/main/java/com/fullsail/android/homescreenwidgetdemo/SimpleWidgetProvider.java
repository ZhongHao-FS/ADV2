package com.fullsail.android.homescreenwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SimpleWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_SHOW_TOAST = "com.fullsail.android.ACTION_SHOW_TOAST";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUtil.updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_SHOW_TOAST)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String toastText = prefs.getString("com.fullsail.android.PREF_TEXT", "Widget Toast");

            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        } else {
            // Don't forget to call the super implementation for other actions
            super.onReceive(context, intent);
        }
    }

}
