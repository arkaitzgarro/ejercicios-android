package com.arkaitzgarro.earthquakes.fragment;

import java.util.HashMap;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EarthQuakeMap extends MapFragment implements
		LoaderManager.LoaderCallbacks<Cursor>, OnMarkerClickListener {

	private static final int LOADER_ID = 1;
	private static final String TAG = "MAP";

	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	private String[] from = { EarthQuakeProvider.Columns.KEY_LOCATION_LAT,
			EarthQuakeProvider.Columns.KEY_LOCATION_LNG,
			EarthQuakeProvider.Columns.KEY_PLACE,
			EarthQuakeProvider.Columns._ID };

	private GoogleMap map;
	private HashMap<Marker, String> earthQuakeMarkers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setupMapIfNeeded();
		earthQuakeMarkers = new HashMap<Marker, String>();

		mCallbacks = this;

		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

		getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
	}
	
	private void setupMapIfNeeded() {
		if(map == null) {
			map = this.getMap();
			
			map.setOnMarkerClickListener(this);
		}
		
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
		
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		
		setupMapIfNeeded();
		
		int id_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns._ID);
		int lat_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_LOCATION_LAT);
		int long_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_LOCATION_LNG);
		int place_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_PLACE);
		
		while(cursor.moveToNext()) {
			MarkerOptions markerPosition = new MarkerOptions();
			markerPosition.position(new LatLng(cursor.getDouble(lat_idx), cursor.getDouble(long_idx)));
			markerPosition.title(cursor.getString(place_idx));
			
			Marker m = map.addMarker(markerPosition);
			builder.include(m.getPosition());
			earthQuakeMarkers.put(m, String.valueOf(cursor.getLong(id_idx)));
		}
		
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 50);
		map.animateCamera(cu);
		
		cursor.close();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		String id = earthQuakeMarkers.get(marker);
		Log.d(TAG, id);
		
		return false;
	}
}
