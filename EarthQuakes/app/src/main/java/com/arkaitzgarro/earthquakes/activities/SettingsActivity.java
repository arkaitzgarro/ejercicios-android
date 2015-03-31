package com.arkaitzgarro.earthquakes.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.SettingsFragment;
import com.arkaitzgarro.earthquakes.services.DownloadEarthquakesService;

/**
 * Created by arkaitz on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String EARTHQUAKE = "EARTHQUAKE";

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
            if (sharedPreferences.getBoolean(key, false)) {
                setAlarm(Integer.parseInt(sharedPreferences.getString(PREF_UPDATE_INTERVAL, "30")));
            } else {
                cancelAlarm();
            }
        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
            setAlarm(Integer.parseInt(sharedPreferences.getString(key, "30")));
        }
    }

    private void setAlarm(int interval) {
        Log.d(EARTHQUAKE, "Switch ON alarm");

        // Get a reference to the Alarm Manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intentToFire = new Intent(this, DownloadEarthquakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intentToFire, 0);

        // Set the alarm to wake the device if sleeping.
        int alarmType = AlarmManager.RTC;

        // Schedule the alarm to repeat every half hour.
        long timeOrLengthofWait = (long) (interval * 60 * 1000);

        alarmManager.setRepeating(alarmType, timeOrLengthofWait, timeOrLengthofWait, alarmIntent);

    }

    private void cancelAlarm() {
        Log.d(EARTHQUAKE, "Switch OFF alarm");

        // Get a reference to the Alarm Manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intentToFire = new Intent(this, DownloadEarthquakesService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
                intentToFire, 0);

        alarmManager.cancel(alarmIntent);
    }

}
