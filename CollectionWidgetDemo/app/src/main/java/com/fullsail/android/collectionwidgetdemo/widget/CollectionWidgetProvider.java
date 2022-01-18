package com.fullsail.android.collectionwidgetdemo.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class CollectionWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        CollectionWidgetHelper.updateWidget(context, appWidgetManager, appWidgetIds);
    }
}
