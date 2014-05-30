package com.arkaitzgarro.earthquakes.activitiy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragment.SettingsFragment;
import com.arkaitzgarro.earthquakes.receiver.EarthquakeAlarmReceiver;

public class SettingsActivity extends Activity implements
		OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key == getResources().getString(R.string.PREF_AUTO_UPDATE)) {
			boolean autoRefresh = prefs.getBoolean(
					getResources().getString(R.string.PREF_AUTO_UPDATE), false);

			if (autoRefresh) {
				// Set a repeting alarm
				startAlarm(prefs);
			} else {
				// Cancel an alarm
				cancelAlarm();
			}
		} else if (key == getResources().getString(R.string.PREF_UPDATE_FREQ)) {
			startAlarm(prefs);
		}
	}

	private void startAlarm(SharedPreferences prefs) {
		int interval = Integer.valueOf(prefs.getString(getResources()
				.getString(R.string.PREF_UPDATE_FREQ), "0"));

		// Get a reference to the Alarm Manager
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(
				EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
				intentToFire, 0);

		// Set the alarm to wake the device if sleeping.
		int alarmType = AlarmManager.RTC;

		// Schedule the alarm to repeat every half hour.
		long timeOrLengthofWait = (long) (interval * 60 * 1000);

		alarmManager.setInexactRepeating(alarmType, timeOrLengthofWait, timeOrLengthofWait,
				alarmIntent);
	}

	private void cancelAlarm() {
		// Get a reference to the Alarm Manager
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(
				EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
				intentToFire, 0);

		alarmManager.cancel(alarmIntent);
	}

}
