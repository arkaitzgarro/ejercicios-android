package com.starky.earthquake;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class EarthquakeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private MainActivity earthquakeActivity;

	private SimpleCursorAdapter adapter;
	private final int LOADER_ID  = 1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		earthquakeActivity = (MainActivity)getActivity();
		new UpdateQuakesTask(earthquakeActivity).execute(getString(R.string.quake_feed));
		
		String[] from = { EarthquakeProvider.KEY_DATE, EarthquakeProvider.KEY_MAGNITUDE, EarthquakeProvider.KEY_DETAILS };
		int[] to = { R.id.date, R.id.magnitude, R.id.place };
		
		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.earthquake_item,
				null,
				from,
				to, 0);
		adapter.setViewBinder(new EarthquakeViewBinder());
		
		setListAdapter(adapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = new String[] { EarthquakeProvider.KEY_ID,
											 EarthquakeProvider.KEY_DATE, 
											 EarthquakeProvider.KEY_MAGNITUDE,
											 EarthquakeProvider.KEY_DETAILS };

		MainActivity earthquakeActivity = (MainActivity) getActivity();
		String where = EarthquakeProvider.KEY_MAGNITUDE + " >= ? ";
		String[] whereArgs = {String.valueOf(earthquakeActivity.getMinimumMagnitude())};

		CursorLoader loader = new CursorLoader(getActivity(),
											   EarthquakeProvider.CONTENT_URI,
											   projection,
											   where,
											   whereArgs,
											   null);

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

	public void refreshEarthquakes() {		
		if (((MainActivity) getActivity()).getLastUpdated().getTime() == 0) {
			// No hay BD
			new UpdateQuakesTask(earthquakeActivity).execute(getString(R.string.quake_feed));
		} else {
			// Filtrar los que hay en la BD
			filterEarthquakes();
		}
	}
	
	public void filterEarthquakes() {	
		getLoaderManager().restartLoader(LOADER_ID, null, this);
	}
}