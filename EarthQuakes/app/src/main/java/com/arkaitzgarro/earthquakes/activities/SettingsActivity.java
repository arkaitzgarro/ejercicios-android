package com.arkaitzgarro.earthquakes.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.SettingsFragment;

/**
 * Created by arkaitz on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);

        // Display the fragment as the main content.
        /*getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();*/
    }
}
