package com.starky.earthquake;

import java.util.ArrayList;
import java.util.Date;

import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;

public class EarthquakeListFragment extends ListFragment {
	
	private UpdateQuakesTask.IUpdateQuakes earthquakeActivity;
	
	private EarthquakeDB dbHelper;

	private ArrayAdapter<Quake> aa;
	private ArrayList<Quake> earthquakes = new ArrayList<Quake>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		earthquakeActivity = (UpdateQuakesTask.IUpdateQuakes)getActivity();
		dbHelper = new EarthquakeDB(getActivity().getApplicationContext());
		dbHelper.open();

		aa = new ArrayAdapter<Quake>(getActivity(),
				android.R.layout.simple_list_item_1, earthquakes);
		setListAdapter(aa);
		
		//new UpdateQuakesTask(earthquakeActivity).execute(getString(R.string.quake_feed));
		refreshEarthquakes();
	}

	public void addNewQuake(Quake _quake) {
		//if (_quake.getMagnitude() > earthquakeActivity.getMinimumMagnitude()) {
			// Add the new quake to our list of earthquakes.
			earthquakes.add(_quake);
			
			// Notify the array adapter of a change.
			aa.notifyDataSetChanged();
			
			ContentValues newValues = new ContentValues();
			newValues.put(EarthquakeDB.KEY_DATE, _quake.getDate().getTime());
			newValues.put(EarthquakeDB.KEY_DETAILS, _quake.getDetails());
			newValues.put(EarthquakeDB.KEY_SUMMARY, _quake.toString());
			newValues.put(EarthquakeDB.KEY_LOCATION_LAT, _quake.getLocation().getLatitude());
			newValues.put(EarthquakeDB.KEY_LOCATION_LNG, _quake.getLocation().getLongitude());
			newValues.put(EarthquakeDB.KEY_MAGNITUDE, _quake.getMagnitude());
			newValues.put(EarthquakeDB.KEY_LINK, _quake.getLink());
			
			dbHelper.createRow(newValues);
		//}
	}
	
	public void refreshEarthquakes() {
		if(((MainActivity)getActivity()).getLastUpdated().getTime() == 0) {
			// No hay BD
			new UpdateQuakesTask(earthquakeActivity).execute(getString(R.string.quake_feed));
		} else {
			// Filtrar los que hay en la BD
			filterEarthquakes();
		}
	}
	
	private void filterEarthquakes() {
		earthquakes.clear();
		Cursor c = dbHelper.query(EarthquakeDB.KEYS_ALL,
				EarthquakeDB.KEY_MAGNITUDE + ">" + ((MainActivity)getActivity()).getMinimumMagnitude(),
				null, null, null, null);
		
		int KEY_DATE_IDX = c.getColumnIndex(EarthquakeDB.KEY_DATE);
		int KEY_DETAILS_IDX = c.getColumnIndex(EarthquakeDB.KEY_DETAILS);
		int KEY_LAT_IDX = c.getColumnIndex(EarthquakeDB.KEY_LOCATION_LAT);
		int KEY_LNG_IDX = c.getColumnIndex(EarthquakeDB.KEY_LOCATION_LNG);
		int KEY_MAGNITUDE_IDX = c.getColumnIndex(EarthquakeDB.KEY_MAGNITUDE);
		int KEY_LINK_IDX = c.getColumnIndex(EarthquakeDB.KEY_LINK);
		
		Log.i("DB", String.valueOf(c.getCount()));
		
		while(c.moveToNext()) {
			Log.i("CURSOR", String.valueOf(c.getFloat(KEY_DATE_IDX)));
			Date qdate = new Date(c.getLong(KEY_DATE_IDX));
			
			Location l = new Location("dummyGPS");
			l.setLatitude(c.getFloat(KEY_LAT_IDX));
			l.setLongitude(c.getFloat(KEY_LNG_IDX));
			
			Quake _quake = new Quake(qdate,
					c.getString(KEY_DETAILS_IDX),
					l,
					c.getFloat(KEY_MAGNITUDE_IDX),
					c.getString(KEY_LINK_IDX));
			
			earthquakes.add(_quake);
		}
		aa.notifyDataSetChanged();
		
		c.close();
	}
}