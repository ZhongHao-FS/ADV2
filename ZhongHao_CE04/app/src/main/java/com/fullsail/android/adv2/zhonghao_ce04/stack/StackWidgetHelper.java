package com.fullsail.android.adv2.zhonghao_ce04.stack;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.fullsail.android.adv2.zhonghao_ce04.R;

public class StackWidgetHelper {
    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int _widgetId) {
        // Create the RemoteViews to represent the stack layout.
        RemoteViews widgetViews = new RemoteViews(_context.getPackageName(), R.layout.widget_stack);

        // set intent for creating the stack's view
        Intent viewIntent = new Intent(_context, StackWidgetService.class);
        viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _widgetId);
        viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
        widgetViews.setRemoteAdapter(R.id.stackView, viewIntent);

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
        ComponentName widgetName = new ComponentName(_context, StackWidgetProvider.class);
        // Get the IDs of our widgets.
        int[] widgetIds = mgr.getAppWidgetIds(widgetName);
        // Tell our widgets to update.
        mgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.stackView);
    }
}
