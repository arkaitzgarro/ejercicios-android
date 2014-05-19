package com.arkaitzgarro.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

//public class PreferencesActivity extends PreferenceActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	public void onBuildHeaders(List<Header> target) {
//		loadHeadersFromResource(R.xml.userpreference_headers, target);
//	}	
//}

public class PreferencesActivity extends Activity implements OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		
		// Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if(key == getResources().getString(R.string.PREF_AUTO_UPDATE)) {
			Log.d("PREFERENCES", String.valueOf(prefs.getBoolean(key, true)));
		} else {
			Log.d("PREFERENCES", prefs.getString(key, "60"));
		}
	}
}
