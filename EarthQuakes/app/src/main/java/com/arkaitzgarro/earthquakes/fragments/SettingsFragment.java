package com.arkaitzgarro.earthquakes.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import com.arkaitzgarro.earthquakes.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);
    }
}
