package com.arkaitzgarro.earthquakes.activitiy;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragment.EarthQuakeList;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask;

public class MainActivity extends Activity implements
		UpdateEarthQuakesTask.IUpdateQuakes {

	EarthQuakeList earthQuakeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		if (savedInstanceState == null) {
			earthQuakeList = new EarthQuakeList();
			// Get references to the Fragments
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.container, earthQuakeList, "list");
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void addQuake(EarthQuake q) {
		earthQuakeList.addNewQuake(q);
	}

}
