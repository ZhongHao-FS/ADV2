package com.fullsail.android.adv2.zhonghao_ce04.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackWidgetViewFactory(getApplicationContext());
    }
}
