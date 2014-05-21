package com.arkaitzgarro.earthquakes.activitiy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeDB;

public class ListActivity extends Activity {

	private EarthQuakeDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		db = EarthQuakeDB.getDB(this);

		EarthQuake q = null;
		q = new EarthQuake("nc72224581", "6km NW of The Geysers, California",
				Long.valueOf("1400577895049"), 3.5, 40.223567, 5.234566, "http://url/");
		Log.d("EARTHQUAKE", String.valueOf(db.insertEarthQuake(q)));
		q = new EarthQuake("nc72224581", "6km NW of The Geysers, California",
				Long.valueOf("1400577895049"), 3.5, 40.223567, 5.234566, "http://url/");
		Log.d("EARTHQUAKE", String.valueOf(db.insertEarthQuake(q)));
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

}
