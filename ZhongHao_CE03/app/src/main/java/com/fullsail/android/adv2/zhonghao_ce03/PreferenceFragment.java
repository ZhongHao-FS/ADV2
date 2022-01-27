package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class PreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    private static final String PREFERENCE_LIST_KEY = "list_preference_1";
    private static final String PREFERENCE_SWITCH_KEY = "switch_preference_1";
    public static final String TAG = "PreferenceFragment.TAG";
    public static String LOCATION = "Orlando";
    public static boolean DARK_THEME = false;

    public static PreferenceFragment newInstance() { return new PreferenceFragment(); }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListPreference localPref = findPreference(PREFERENCE_LIST_KEY);
        if (localPref != null) {
            localPref.setOnPreferenceChangeListener(this);
        }

        SwitchPreference themePref = findPreference(PREFERENCE_SWITCH_KEY);
        if (themePref != null) {
            themePref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SharedPreferences dPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor edit = dPrefs.edit();

        if (preference.getKey().equals(PREFERENCE_LIST_KEY)) {
            edit.putString(PREFERENCE_LIST_KEY, newValue.toString());
            LOCATION = newValue.toString();
        }
        if (preference.getKey().equals(PREFERENCE_SWITCH_KEY)) {
            edit.putBoolean(PREFERENCE_SWITCH_KEY, newValue.equals(true));
            DARK_THEME = newValue.equals(true);
        }
        edit.apply();

        return true;
    }
}
