package com.fullsail.android.adv2.zhonghao_ce04.flipper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.fullsail.android.adv2.zhonghao_ce04.R;

public class FlipperWidgetHelper {
    public static final String REFRESH_ACTION = "com.fullsail.android.adv2.zhonghao_ce04.ImageDataUtil.REFRESH_ACTION";

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int _widgetId) {
        // Create the RemoteViews to represent the stack layout.
        RemoteViews widgetViews = new RemoteViews(_context.getPackageName(), R.layout.widget_flipper);

        // set intent for creating the stack's view
        Intent viewIntent = new Intent(_context, FlipperWidgetService.class);
        viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _widgetId);
        viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
        widgetViews.setRemoteAdapter(R.id.flipperView, viewIntent);

        // Refresh action by pressing the button
        Intent refreshIntent = new Intent(_context, FlipperWidgetProvider.class);
        refreshIntent.setAction(REFRESH_ACTION);
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _widgetId);
        refreshIntent.setData(Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(_context, 0, refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetViews.setPendingIntentTemplate(R.id.flipperView, refreshPendingIntent);

        // Update the stack.
        _appWidgetManager.updateAppWidget(_widgetId, widgetViews);
    }

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int[] _appWidgetIds) {
        for (int appWidgetId : _appWidgetIds) {
            updateWidget(_context, _appWidgetManager, appWidgetId);
        }
    }

    public static void notifyDataChanged(Context _context) {
        // Get our widget manager to access widget functions.
        AppWidgetManager mgr = AppWidgetManager.getInstance(_context);
        // Create a component that represents our widget provider.
        ComponentName widgetName = new ComponentName(_context, FlipperWidgetProvider.class);
        // Get the IDs of our widgets.
        int[] widgetIds = mgr.getAppWidgetIds(widgetName);
        // Tell our widgets to update.
        mgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.flipperView);
    }
}
