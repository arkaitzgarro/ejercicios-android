package com.arkaitzgarro.earthquakes.fragment;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask;

public class EarthQuakeList extends ListFragment {
	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private static final String TAG = "EARTHQUAKE";

	private EarthQuakeDB db;

	private EarthQuakeArrayAdapter aa;
	private ArrayList<EarthQuake> earthquakes;

	private UpdateEarthQuakesTask.IUpdateQuakes earthquakeActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		db = EarthQuakeDB.getDB(inflater.getContext());
		earthquakes = db.getEarthquakesByMagnitude(0);

		// Create the array adapter to bind the array to the listview
		aa = new EarthQuakeArrayAdapter(inflater.getContext(), earthquakes);

		setListAdapter(aa);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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

	public void refreshEarthquakes() {
		new UpdateEarthQuakesTask(earthquakeActivity)
				.execute(getString(R.string.quake_feed));
	}
	
	public void addNewQuake(EarthQuake q) {
		Log.d(TAG, "RECEIVED: " + q);
		db.insertEarthQuake(q);
		earthquakes.add(q);
		
		aa.notifyDataSetChanged();
	}

	// @Override
	// public void onActivityCreated(Bundle inState) {
	// super.onActivityCreated(inState);
	//
	// if (inState != null) {
	// // A–adir a la lista
	// todoItems.addAll(inState.getStringArrayList(ITEMS_ARRAY));
	// aa.notifyDataSetChanged();
	// }
	// }
	//
	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// outState.putStringArrayList(ITEMS_ARRAY, todoItems);
	//
	// super.onSaveInstanceState(outState);
	// }

}
