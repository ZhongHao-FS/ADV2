package com.fullsail.android.adv2.zhonghao_ce03;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetUtil {
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        settingsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, widgetId, settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetViews.setOnClickPendingIntent(R.id.imageButton, settingsPendingIntent);

        appWidgetManager.updateAppWidget(widgetId, widgetViews);
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
