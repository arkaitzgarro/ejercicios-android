package com.arkaitzgarro.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.SettingsFragment;

/**
 * Created by arkaitz on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }



}
