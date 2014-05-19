package com.arkaitzgarro.sharedpreferences;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.userpreference_headers, target);
	}	
}

//public class PreferencesActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		// Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new PreferencesFragment())
//                .commit();
//	}
//}
