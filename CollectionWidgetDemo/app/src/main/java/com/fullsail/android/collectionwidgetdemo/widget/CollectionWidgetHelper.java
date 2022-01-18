package com.fullsail.android.collectionwidgetdemo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;

public class CollectionWidgetHelper {

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int _widgetId) {

    }

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int[] _appWidgetIds) {
        for(int i = 0; i < _appWidgetIds.length; i++) {
            updateWidget(_context, _appWidgetManager, _appWidgetIds[i]);
        }
    }
}
