package com.fullsail.android.adv2.zhonghao_ce07;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DrawingFragment extends Fragment {
    public static final String TAG = "DrawingFragment.TAG";

    public static DrawingFragment newInstance() {
        return new DrawingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawing, container, false);
    }
}
