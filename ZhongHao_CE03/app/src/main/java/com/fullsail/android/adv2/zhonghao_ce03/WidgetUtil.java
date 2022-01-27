package com.fullsail.android.adv2.zhonghao_ce03;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;


import java.util.ArrayList;


public class WidgetUtil {
    public static boolean mForecast = false;

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        settingsIntent.setAction(SimpleWidgetProvider.ACTION_CONFIG_PREF);
        settingsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, widgetId, settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE);
        widgetViews.setOnClickPendingIntent(R.id.imageButton_2, settingsPendingIntent);

        ArrayList<String> weatherStrings = FileUtility.readArrayList(context);
        widgetViews.setTextViewText(R.id.textView, weatherStrings.get(0) + "\n" + weatherStrings.get(1) + "\n" + weatherStrings.get(2));

        Uri uri = Uri.parse(weatherStrings.get(3));
        widgetViews.setImageViewUri(R.id.imageButton, uri);

        Intent forecastIntent = new Intent(context, ForecastActivity.class);
        forecastIntent.setAction(SimpleWidgetProvider.ACTION_FORECAST);
        forecastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent forecastPendingIntent = PendingIntent.getActivity(context, widgetId, forecastIntent, PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE);
        widgetViews.setOnClickPendingIntent(R.id.imageButton, forecastPendingIntent);

        appWidgetManager.updateAppWidget(widgetId, widgetViews);
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
