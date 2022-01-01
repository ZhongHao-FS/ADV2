package com.fullsail.android.canvasdrawing;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DrawingFragment extends Fragment {
    public static final String TAG = "DrawingFragment.TAG";

    public static DrawingFragment newInstance() {
        return new DrawingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawing_fragment, container, false);
    }
}
