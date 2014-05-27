package com.arkaitzgarro.earthquakes.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activitiy.DetailActivity;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask;

public class EarthQuakeList extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";
	public final static String ID = "_id";

	private static final String TAG = "EARTHQUAKE";

	// The loader's unique id. Loader ids are specific to the Activity or
	// Fragment in which they reside.
	private static final int LOADER_ID = 1;

	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	private String[] from = { EarthQuakeProvider.Columns.KEY_TIME,
			EarthQuakeProvider.Columns.KEY_MAGNITUDE,
			EarthQuakeProvider.Columns.KEY_PLACE,
			EarthQuakeProvider.Columns._ID };
	private int[] to = { R.id.time, R.id.magnitude, R.id.place };
	private SimpleCursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.earthquake_row_layout, null, from, to, 0);

		adapter.setViewBinder(new EarthquakeViewBinder());
		setListAdapter(adapter);
		
		mCallbacks = this;
		
		LoaderManager lm = getLoaderManager();
	    lm.initLoader(LOADER_ID, null, mCallbacks);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		refreshEarthquakes();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent detail = new Intent(getActivity(), DetailActivity.class);
		detail.putExtra(ID, id);

		startActivity(detail);
	}

	public void refreshEarthquakes() {
		new UpdateEarthQuakesTask(getActivity())
				.execute(getString(R.string.quake_feed));
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		String minMag = prefs.getString(
				getResources().getString(R.string.PREF_MIN_MAG), "0");

		String where = EarthQuakeProvider.Columns.KEY_MAGNITUDE + " >= ? ";
		String[] whereArgs = { minMag };
		String order = EarthQuakeProvider.Columns.KEY_TIME + " DESC";

		CursorLoader loader = new CursorLoader(getActivity(),
				EarthQuakeProvider.CONTENT_URI, from, where, whereArgs, order);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
