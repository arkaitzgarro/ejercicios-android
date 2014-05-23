package com.arkaitzgarro.earthquakes.fragment;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activitiy.DetaillActivity;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
//import com.arkaitzgarro.earthquakes.provider.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask;

public class EarthQuakeList extends ListFragment {
	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";
	public final static String ID = "_id";

	private static final String TAG = "EARTHQUAKE";

//	private EarthQuakeDB db;

	private EarthQuakeArrayAdapter aa;
	private ArrayList<EarthQuake> earthquakes;
	
	private SharedPreferences prefs;
	private double minMag;

	private UpdateEarthQuakesTask.IUpdateQuakes earthquakeActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		minMag = Double.valueOf(prefs.getString(getResources().getString(R.string.PREF_MIN_MAG), "0"));
		
//		db = EarthQuakeDB.getDB(getActivity());
		earthquakes = new ArrayList<EarthQuake>();
		// Create the array adapter to bind the array to the listview
		aa = new EarthQuakeArrayAdapter(getActivity(), earthquakes);
		setListAdapter(aa);

		try {
			earthquakeActivity = (UpdateEarthQuakesTask.IUpdateQuakes) getActivity();
		} catch (ClassCastException ex) {
			Log.e(TAG, earthquakeActivity.toString()
					+ " must implement UpdateQuakesTask.IUpdateQuakes");
			throw new ClassCastException(earthquakeActivity.toString()
					+ " must implement UpdateQuakesTask.IUpdateQuakes");
		}
		
		refreshEarthquakes();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		minMag = Double.valueOf(prefs.getString(getResources().getString(R.string.PREF_MIN_MAG), "0"));
		
		earthquakes.clear();
//		earthquakes.addAll(db.getEarthquakesByMagnitude(minMag));
		aa.notifyDataSetChanged();
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Intent detail = new Intent(getActivity(), DetaillActivity.class);
        detail.putExtra(ID, earthquakes.get(position).getId());
        
        startActivity(detail);
    }

	public void refreshEarthquakes() {
		new UpdateEarthQuakesTask(earthquakeActivity)
				.execute(getString(R.string.quake_feed));
	}
	
	public void addNewQuake(EarthQuake q) {
		Log.d(TAG, "RECEIVED: " + q);
		
		
		if(q.getId() > 0 && q.getMagnitude() >= minMag) {
			earthquakes.add(0, q);
			aa.notifyDataSetChanged();
			Log.d(TAG, "ADDED: " + q);
		}		
	}

}
