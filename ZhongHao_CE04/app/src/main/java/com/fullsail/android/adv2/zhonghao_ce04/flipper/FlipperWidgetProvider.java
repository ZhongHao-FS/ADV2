package com.fullsail.android.adv2.zhonghao_ce04.flipper;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.fullsail.android.adv2.zhonghao_ce04.stack.StackWidgetHelper;

public class FlipperWidgetProvider extends AppWidgetProvider {
    public static final String REFRESH_ACTION = "com.fullsail.android.adv2.zhonghao_ce04.ImageDataUtil.REFRESH_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        FlipperWidgetHelper.updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(REFRESH_ACTION)) {
            FlipperWidgetHelper.notifyDataChanged(context);
            StackWidgetHelper.notifyDataChanged(context);
        }

        super.onReceive(context, intent);
    }
}
