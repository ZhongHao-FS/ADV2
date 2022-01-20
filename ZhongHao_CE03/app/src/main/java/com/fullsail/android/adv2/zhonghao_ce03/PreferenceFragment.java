package com.fullsail.android.adv2.zhonghao_ce03;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);
    }


}
