package com.arkaitzgarro.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends Activity {

	public static final String USER_PREFERENCE = "USER_PREFERENCE";
	public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";
	public static final String PREF_UPDATE_FREQ_INDEX = "PREF_UPDATE_FREQ_INDEX";
	
	private Switch autoRefresh;
	private Spinner interval;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		prefs = getSharedPreferences(USER_PREFERENCE, Activity.MODE_PRIVATE);

		autoRefresh = (Switch) findViewById(R.id.switchAuto);
		interval = (Spinner) findViewById(R.id.spinnerInterval);

		populateSpinners();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		updateUIFromPreferences();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		savePreferences();
	}
	
	private void updateUIFromPreferences() {
		boolean autoUpChecked = prefs.getBoolean(PREF_AUTO_UPDATE, false);
		int updateFreqIndex = prefs.getInt(PREF_UPDATE_FREQ_INDEX, 2);

		interval.setSelection(updateFreqIndex);
		autoRefresh.setChecked(autoUpChecked);
	}
	
	private void savePreferences() {
		boolean autoUpdateChecked = autoRefresh.isChecked();
		int updateIndex = interval.getSelectedItemPosition();
		
		Editor editor = prefs.edit();
		editor.putBoolean(PREF_AUTO_UPDATE, autoUpdateChecked);
		editor.putInt(PREF_UPDATE_FREQ_INDEX, updateIndex);
		editor.commit();
	}

	private void populateSpinners() {
		// Populate the update frequency spinner
		ArrayAdapter<CharSequence> fAdapter;
		
		fAdapter = ArrayAdapter.createFromResource(this,
				R.array.update_freq_options,
				android.R.layout.simple_spinner_item);
		fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		interval.setAdapter(fAdapter);
	}
}
