package com.fullsail.android.adv2.zhonghao_ce04.flipper;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FlipperWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FlipperWidgetViewFactory(getApplicationContext());
    }
}
