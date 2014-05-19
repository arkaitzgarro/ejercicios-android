package com.arkaitzgarro.sharedpreferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private final int ACTION_SETTINGS = 1;
	private final int ACTION_PREFERENCES = 2;
	
	private TextView autoRefreshValue;
	private TextView intervalValue;
	private TextView pfAutoRefreshValue;
	private TextView pfIntervalValue;
	
	private SharedPreferences prefs;
	private SharedPreferences pf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = getSharedPreferences(SettingsActivity.USER_PREFERENCE, Activity.MODE_PRIVATE);
		pf = PreferenceManager.getDefaultSharedPreferences(this);
		
		autoRefreshValue = (TextView) findViewById(R.id.autoRefreshValue);
		intervalValue = (TextView) findViewById(R.id.intervalValue);
		pfAutoRefreshValue = (TextView) findViewById(R.id.pfAutoRefreshValue);
		pfIntervalValue = (TextView) findViewById(R.id.pfIntervalValue);
		
		updateFromPreferences();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTION_SETTINGS | requestCode == ACTION_PREFERENCES) {
			updateFromPreferences();
		}
	}
	
	private void updateFromPreferences() {
		// Get the option values from the arrays.
		String[] freqValues = getResources().getStringArray(R.array.update_freq_values);

		boolean autoUpdateChecked = prefs.getBoolean(
				SettingsActivity.PREF_AUTO_UPDATE, false);
		int intervalIdx = prefs.getInt(
				SettingsActivity.PREF_UPDATE_FREQ_INDEX, 2);
		
		autoRefreshValue.setText((autoUpdateChecked)?"On":"Off");
		intervalValue.setText(freqValues[intervalIdx]);
		
		boolean pfAutoUpdateChecked = pf.getBoolean(
				SettingsActivity.PREF_AUTO_UPDATE, false);
		int pfInterval = Integer.parseInt(pf.getString(
				SettingsActivity.PREF_UPDATE_FREQ_INDEX, "2"));
		
		pfAutoRefreshValue.setText((pfAutoUpdateChecked)?"On":"Off");
//		pfIntervalValue.setText(pfInterval);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, ACTION_SETTINGS);
			return true;
		} else if(id == R.id.action_preferences) {
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivityForResult(i, ACTION_PREFERENCES);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
