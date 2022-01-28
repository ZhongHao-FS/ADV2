package com.fullsail.android.adv2.zhonghao_ce04;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private static final long BASE_ID = 0x1003;
    private final Context mContext;
    private final ArrayList<String> mImagePaths;

    public GridAdapter(Context context, ArrayList<String> imagePaths) {
        mContext = context;
        mImagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return mImagePaths.size();
    }

    @Override
    public Object getItem(int i) {
        if (i >= 0 && i < mImagePaths.size()) {
            return mImagePaths.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return BASE_ID + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        String imagePath = (String) getItem(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_cell, viewGroup, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.iv.setImageResource(R.drawable.ic_launcher_background);
        Log.i("getView", "setResource");
        if (imagePath != null) {
            Uri imageUri = Uri.parse(imagePath);
            vh.iv.setImageURI(imageUri);
            Log.i("image", "setUri");
        }
        return view;
    }

    static class ViewHolder {
        ImageView iv;

        public ViewHolder(View _layout) {
            iv = _layout.findViewById(R.id.imageView);
        }
    }
}