package com.fullsail.android.homescreenwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;

public class SecondWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_SELECT_IMAGE = "com.fullsail.android.ACTION_SELECT_IMAGE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUtil.updateWidget2(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_SELECT_IMAGE)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String imageName = prefs.getString("imageNameArray", "Horse Shoe");

        } else {
            // Don't forget to call the super implementation for other actions
            super.onReceive(context, intent);
        }
    }
}
