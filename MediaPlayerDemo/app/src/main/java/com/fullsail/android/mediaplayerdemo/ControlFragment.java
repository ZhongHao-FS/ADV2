package com.fullsail.android.mediaplayerdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ControlFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = "ControlFragment.TAG";

    public static ControlFragment newInstance() {
        return new ControlFragment();
    }

    public interface PlaybackCommandListener {
        void play();
        void pause();
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
        } else if (view.getId() == R.id.button_stop) {
            mListener.stop();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_controls, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View root = getView();
        if(root != null) {
            root.findViewById(R.id.button_play).setOnClickListener(this);
            root.findViewById(R.id.button_stop).setOnClickListener(this);
            root.findViewById(R.id.button_pause).setOnClickListener(this);
        }
    }
}
