package com.fullsail.android.homescreenwidgetdemo;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class ConfigFragment2 extends PreferenceFragmentCompat {
    public static ConfigFragment2 newInstance() {
        return new ConfigFragment2();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs_config, rootKey);
    }
}
