package com.starky.earthquake;

import java.util.Date;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements UpdateQuakesTask.IUpdateQuakes {

	private static final int SHOW_PREFERENCES = 1;
	private static final int MENU_PREFERENCES = Menu.FIRST + 1;
	private static final int MENU_UPDATE = Menu.FIRST + 2;

	private int minimumMagnitude;
	private boolean autoUpdateChecked;
	private int updateFreq;
	private Date lastUpdated;
	
	EarthquakeListFragment earthquakeList;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getFragmentManager();
		earthquakeList = (EarthquakeListFragment) fm.findFragmentById(R.id.EarthquakeListFragment);
		
		Context context = getApplicationContext();
	    prefs = PreferenceManager.getDefaultSharedPreferences(context);

		updateFromPreferences();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == SHOW_PREFERENCES) {
			updateFromPreferences();
		
			earthquakeList.refreshEarthquakes();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		//menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
			case (R.id.action_settings): {
				Intent i = new Intent(this, PreferencesActivity.class);
				startActivityForResult(i, SHOW_PREFERENCES);
				return true;
			}
		}
		return false;
	}
	
	private void updateFromPreferences() {
	    minimumMagnitude = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_MIN_MAG_INDEX, "3"));
	    updateFreq = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_UPDATE_FREQ_INDEX, "60"));
	    autoUpdateChecked = prefs.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false);
	    lastUpdated = new Date(prefs.getLong(PreferencesActivity.PREF_LAST_UPDATE_INDEX, 0));
	  }

	public int getMinimumMagnitude() {
		return minimumMagnitude;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated() {
		lastUpdated = new Date();
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(PreferencesActivity.PREF_LAST_UPDATE_INDEX, System.currentTimeMillis());
		editor.apply();
	}

	@Override
	public void addQuake(Quake q) {
		earthquakeList.addNewQuake(q);
		setLastUpdated();
	}

}
