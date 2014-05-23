package com.arkaitzgarro.earthquake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class PreferencesActivity extends Activity {

	public static final String USER_PREFERENCE = "USER_PREFERENCE";
	public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";
	public static final String PREF_MIN_MAG_INDEX = "PREF_MIN_MAG_INDEX";
	public static final String PREF_UPDATE_FREQ_INDEX = "PREF_UPDATE_FREQ_INDEX";

	private SharedPreferences prefs;

	private CheckBox autoUpdate;
	private Spinner updateFreqSpinner;
	private Spinner magnitudeSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);

		updateFreqSpinner = (Spinner) findViewById(R.id.spinner_update_freq);
		magnitudeSpinner = (Spinner) findViewById(R.id.spinner_quake_mag);
		autoUpdate = (CheckBox) findViewById(R.id.checkbox_auto_update);

		populateSpinners();

		Context context = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);

		updateUIFromPreferences();
	}

	private void populateSpinners() {
		// Populate the update frequency spinner
		ArrayAdapter<CharSequence> fAdapter;
		fAdapter = ArrayAdapter.createFromResource(this,
				R.array.update_freq_options,
				android.R.layout.simple_spinner_item);
		fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		updateFreqSpinner.setAdapter(fAdapter);

		// Populate the minimum magnitude spinner
		ArrayAdapter<CharSequence> mAdapter;
		mAdapter = ArrayAdapter
				.createFromResource(this, R.array.magnitude_options,
						android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		magnitudeSpinner.setAdapter(mAdapter);
	}

	private void updateUIFromPreferences() {
		boolean autoUpChecked = prefs.getBoolean(PREF_AUTO_UPDATE, false);
		int updateFreqIndex = prefs.getInt(PREF_UPDATE_FREQ_INDEX, 2);
		int minMagIndex = prefs.getInt(PREF_MIN_MAG_INDEX, 0);

		updateFreqSpinner.setSelection(updateFreqIndex);
		magnitudeSpinner.setSelection(minMagIndex);
		autoUpdate.setChecked(autoUpChecked);
	}

	private void savePreferences() {
		int updateIndex = updateFreqSpinner.getSelectedItemPosition();
		int minMagIndex = magnitudeSpinner.getSelectedItemPosition();
		boolean autoUpdateChecked = autoUpdate.isChecked();
		
		Editor editor = prefs.edit();
		editor.putBoolean(PREF_AUTO_UPDATE, autoUpdateChecked);
		editor.putInt(PREF_UPDATE_FREQ_INDEX, updateIndex);
		editor.putInt(PREF_MIN_MAG_INDEX, minMagIndex);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

}
