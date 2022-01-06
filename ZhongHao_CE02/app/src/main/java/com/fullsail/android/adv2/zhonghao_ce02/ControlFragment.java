package com.fullsail.android.adv2.zhonghao_ce02;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ControlFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "ControlFragment.TAG";

    public static ControlFragment newInstance() {
        return new ControlFragment();
    }

    public interface PlaybackCommandListener {
        void play();
        void pause();
        void next();
        void previous();
        void stop();
    }

    private PlaybackCommandListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PlaybackCommandListener) {
            mListener = (PlaybackCommandListener) context;
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        if (view.getId() == R.id.button_play) {
            mListener.play();
        } else if (view.getId() == R.id.button_pause) {
            mListener.pause();
        } else if (view.getId() == R.id.button_next) {
            mListener.next();
        } else if (view.getId() == R.id.button_previous) {
            mListener.previous();
        } else if (view.getId() == R.id.button_stop) {
            mListener.stop();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View root = getView();
        if(root != null) {
            root.findViewById(R.id.button_play).setOnClickListener(this);
            root.findViewById(R.id.button_stop).setOnClickListener(this);
            root.findViewById(R.id.button_pause).setOnClickListener(this);
            root.findViewById(R.id.button_previous).setOnClickListener(this);
            root.findViewById(R.id.button_next).setOnClickListener(this);
        }
    }
}
