package com.fullsail.android.homescreenwidgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetUtil {
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        // Create our RemoteViews based on our initial layout
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Create a PendingIntent with our custom action
        Intent toastIntent = new Intent(context, SimpleWidgetProvider.class);
        toastIntent.setAction(SimpleWidgetProvider.ACTION_SHOW_TOAST);
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Attach the PendingIntent to our toast button
        widgetViews.setOnClickPendingIntent(R.id.button_toast, toastPendingIntent);

        // Create a PendingIntent to open the config activity
        Intent configIntent = new Intent(context, ConfigActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        // Keep in mind PendingIntent uniqueness rules. Use the widget ID as request code
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, widgetId, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Attach the PendingIntent to our config button
        widgetViews.setOnClickPendingIntent(R.id.button_config, configPendingIntent);

        // Update the widget with newly configured RemoteViews
        appWidgetManager.updateAppWidget(widgetId, widgetViews);
    }

    static void updateWidget2(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        // Create our RemoteViews based on our initial layout
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget2_layout);

        // Create a PendingIntent to open the config activity
        Intent configIntent = new Intent(context, ConfigActivity2.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        // Keep in mind PendingIntent uniqueness rules. Use the widget ID as request code
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, widgetId, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Attach the PendingIntent to our config button
        widgetViews.setOnClickPendingIntent(R.id.button_select, configPendingIntent);


        // Update the widget with newly configured RemoteViews
        appWidgetManager.updateAppWidget(widgetId, widgetViews);
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateWidget2(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateWidget2(context, appWidgetManager, appWidgetId);
        }
    }
}
