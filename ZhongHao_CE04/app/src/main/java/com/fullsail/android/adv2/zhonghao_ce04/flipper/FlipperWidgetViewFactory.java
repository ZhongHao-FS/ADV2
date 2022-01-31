package com.fullsail.android.adv2.zhonghao_ce04.flipper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.fullsail.android.adv2.zhonghao_ce04.ImageDataUtil;
import com.fullsail.android.adv2.zhonghao_ce04.R;

import java.util.ArrayList;

public class FlipperWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private ArrayList<Uri> mUriList;

    public FlipperWidgetViewFactory(Context _context) {
        mContext = _context;
        mUriList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        mUriList = ImageDataUtil.loadGallery(mContext);
    }

    @Override
    public void onDataSetChanged() {
        mUriList = ImageDataUtil.loadGallery(mContext);
    }

    @Override
    public void onDestroy() {
        mUriList.clear();
        mUriList = null;
    }

    @Override
    public int getCount() {
        return mUriList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews flipperWV = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Uri uri = mUriList.get(i);
        flipperWV.setImageViewUri(R.id.imageView2, uri);

        // Set intent for item click
        Intent fillInIntent = new Intent(Intent.ACTION_VIEW);
        fillInIntent.setData(uri);
        flipperWV.setOnClickFillInIntent(R.id.widgetItem, fillInIntent);

        return flipperWV;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
