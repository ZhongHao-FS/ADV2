package com.fullsail.android.collectionwidgetdemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.fullsail.android.collectionwidgetdemo.DetailsActivity;
import com.fullsail.android.collectionwidgetdemo.FormActivity;
import com.fullsail.android.collectionwidgetdemo.R;

public class CollectionWidgetHelper {

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int _widgetId) {
        // Create the RemoteViews to represent the widget layout.
        RemoteViews widgetViews = new RemoteViews(_context.getPackageName(), R.layout.collection_widget_layout);

        Intent formIntent = new Intent(_context, FormActivity.class);
        PendingIntent formPendingIntent = PendingIntent.getActivity(_context, 0, formIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetViews.setOnClickPendingIntent(R.id.button_new, formPendingIntent);

        // Create an intent that points to our widget service.
        Intent remoteIntent = new Intent(_context, CollectionWidgetService.class);
        // Attach that intent to the RemoteViews as our remote adapter.
        widgetViews.setRemoteAdapter(R.id.list_people, remoteIntent);
        // Set which view should be used as the empty view.
        widgetViews.setEmptyView(R.id.list_people, R.id.text_empty);

        // Create a PendingIntent template to open the DetailsActivity.
        Intent detailsIntent = new Intent(_context, DetailsActivity.class);
        PendingIntent detailsPendingIntent = PendingIntent.getActivity(_context, 0, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Set our PendingIntent to be our template and attach it to the list.
        widgetViews.setPendingIntentTemplate(R.id.list_people, detailsPendingIntent);
        // Update the widget.
        _appWidgetManager.updateAppWidget(_widgetId, widgetViews);
    }

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int[] _appWidgetIds) {
        for(int i = 0; i < _appWidgetIds.length; i++) {
            updateWidget(_context, _appWidgetManager, _appWidgetIds[i]);
        }
    }

    public static void notifyDataChanged(Context _context) {
        // Get our widget manager to access widget functions.
        AppWidgetManager mgr = AppWidgetManager.getInstance(_context);
        // Create a component that represents our widget provider.
        ComponentName widgetName = new ComponentName(_context, CollectionWidgetProvider.class);
        // Get the IDs of our widgets.
        int[] widgetIds = mgr.getAppWidgetIds(widgetName);
        // Tell our widgets to update their person list.
        mgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_people);
    }
}
