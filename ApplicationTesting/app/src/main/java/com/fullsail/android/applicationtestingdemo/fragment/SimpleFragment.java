package com.fullsail.android.applicationtestingdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullsail.android.applicationtestingdemo.R;

public class SimpleFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "SimpleFragment.TAG";

    private static final String ARG_DISPLAY_VALUE = "SimpleFragment.ARG_DISPLAY_VALUE";

    public static SimpleFragment newInstance(String _displayValue) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();

        if(_displayValue != null) {
            args.putString(ARG_DISPLAY_VALUE, _displayValue);
        }

        fragment.setArguments(args);
        return fragment;
    }

    public interface OnActionButtonClickedListener {
        void onActionButtonClicked();
    }

    private OnActionButtonClickedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnActionButtonClickedListener) {
            mListener = (OnActionButtonClickedListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if(view != null) {
            view.findViewById(R.id.button_action).setOnClickListener(this);
        }

        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_DISPLAY_VALUE)) {
            setDisplayString(args.getString(ARG_DISPLAY_VALUE));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_action && mListener != null) {
            mListener.onActionButtonClicked();
        }
    }

    public void setDisplayString(String _displayString) {
        View view = getView();

        if(view == null) {
            return;
        }

        TextView displayText = (TextView)view.findViewById(R.id.text_display);
        displayText.setText(_displayString);
    }
}
