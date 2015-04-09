package com.arkaitzgarro.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.SettingsFragment;
import com.arkaitzgarro.earthquakes.managers.EarthQuakeAlarmManager;

/**
 * Created by arkaitz on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register this OnSharedPreferenceChangeListener
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Display the fragment as the main content.
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String PREF_AUTO_UPDATE = getString(R.string.PREF_AUTO_UPDATE);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);

        if (key.equals(PREF_AUTO_UPDATE)) {
            // Start / stop
            if (sharedPreferences.getBoolean(key, true)) {
                long interval = Long.parseLong(sharedPreferences.getString(PREF_UPDATE_INTERVAL, "1"));
                EarthQuakeAlarmManager.setAlarm(this, interval * 60 * 1000);
            } else {
                EarthQuakeAlarmManager.cancelAlarm(this);
            }
        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
            long interval = Long.parseLong(sharedPreferences.getString(PREF_UPDATE_INTERVAL, "1"));
            EarthQuakeAlarmManager.updateAlarm(this, interval * 60 * 1000);
        }
    }
}
