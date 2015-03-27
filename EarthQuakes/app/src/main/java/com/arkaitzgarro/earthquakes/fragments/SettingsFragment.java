package com.arkaitzgarro.earthquakes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arkaitzgarro.earthquakes.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String EARTHQUAKES = "EARTHQUAKES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register this OnSharedPreferenceChangeListener
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.earthquakes_preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String PREF_AUTO_UPDATE = getString(R.string.PREF_AUTO_UPDATE);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        String PREF_MIN_MAG = getString(R.string.PREF_MIN_MAG);

        if (key.equals(PREF_AUTO_UPDATE)) {
            // Start/Stop auto refresh
        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
            // Change auto refresh interval
        }
    }
}
