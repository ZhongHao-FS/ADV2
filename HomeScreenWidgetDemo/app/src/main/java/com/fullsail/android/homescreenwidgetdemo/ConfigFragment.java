package com.fullsail.android.homescreenwidgetdemo;


import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

public class ConfigFragment extends PreferenceFragmentCompat {

    public static final String TAG = "ConfigFragment.TAG";

    public static ConfigFragment newInstance() {
        return new ConfigFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs_config, rootKey);
    }
}
