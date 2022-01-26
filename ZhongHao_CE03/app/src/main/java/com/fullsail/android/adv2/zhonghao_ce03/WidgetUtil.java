package com.fullsail.android.adv2.zhonghao_ce03;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class WidgetUtil {
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        updateWeather(context);

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

    static void updateWeather(Context context) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class).setConstraints(constraints).build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }

    static void updateUI() {

    }
}
