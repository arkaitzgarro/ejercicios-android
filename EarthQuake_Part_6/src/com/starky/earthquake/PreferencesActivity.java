package com.starky.earthquake;

import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Spinner;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	public static final String USER_PREFERENCE = "USER_PREFERENCE";
	public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";
	public static final String PREF_MIN_MAG_INDEX = "PREF_MIN_MAG";
	public static final String PREF_UPDATE_FREQ_INDEX = "PREF_UPDATE_FREQ";
	public static final String PREF_LAST_UPDATE_INDEX = "PREF_LAST_UPDATE";

	private SharedPreferences prefs;
	private SharedPreferences.OnSharedPreferenceChangeListener listener;

	private CheckBox autoUpdate;
	private Spinner updateFreqSpinner;
	private Spinner magnitudeSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		String minMagIndex = sharedPreferences.getString(PreferencesActivity.PREF_MIN_MAG_INDEX, "0");
		String freqIndex = sharedPreferences.getString(PreferencesActivity.PREF_UPDATE_FREQ_INDEX, "0");
		boolean autoUpdateChecked = sharedPreferences.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false);
	}

}
