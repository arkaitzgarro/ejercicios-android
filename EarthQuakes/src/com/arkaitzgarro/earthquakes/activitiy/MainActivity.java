package com.arkaitzgarro.earthquakes.activitiy;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragment.EarthQuakeList;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask;

public class MainActivity extends Activity implements
		UpdateEarthQuakesTask.IUpdateQuakes {
	
	private static final String TAG = "EARTHQUAKE";
	private int ACTION_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		if (savedInstanceState == null) {
			// Get references to the Fragments
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.container, new EarthQuakeList(), "list");
			fragmentTransaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, ACTION_SETTINGS);
			return true;
		} else if(id == R.id.action_refresh) {
			((EarthQuakeList)getFragmentManager().findFragmentByTag("list")).refreshEarthquakes();
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void addQuake(EarthQuake q) {
		Log.d(TAG, "CONTEXT " + this);
		
		((EarthQuakeList)getFragmentManager().findFragmentByTag("list")).addNewQuake(q);
	}

}
