package com.arkaitzgarro.earthquakes.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.arkaitzgarro.earthquakes.R;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
    }

}
