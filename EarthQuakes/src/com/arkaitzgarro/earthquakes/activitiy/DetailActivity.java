package com.arkaitzgarro.earthquakes.activitiy;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragment.EarthQuakeList;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;

public class DetailActivity extends Activity implements LoaderCallbacks<Cursor> {

	private static final String TAG = "EARTHQUAKE";
	
	private TextView txtMagnitude;
	private TextView txtPlace;

	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	private static final int LOADER_ID = 2;
	private long earthquake_id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		earthquake_id = getIntent().getLongExtra(EarthQuakeList.ID, 0);
		
		txtMagnitude = (TextView)findViewById(R.id.detail_magnitude);
		txtPlace = (TextView)findViewById(R.id.detail_place);

		mCallbacks = this;

		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detaill, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri rowAddress = ContentUris.withAppendedId(
				EarthQuakeProvider.CONTENT_URI, earthquake_id);

		CursorLoader loader = new CursorLoader(DetailActivity.this, rowAddress,
				EarthQuakeProvider.KEYS_ALL, null, null, null);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int mag_idx = cursor
				.getColumnIndex(EarthQuakeProvider.Columns.KEY_MAGNITUDE);
		int place_idx = cursor
				.getColumnIndex(EarthQuakeProvider.Columns.KEY_PLACE);

		if (cursor.moveToFirst()) {
			String magnitude = cursor.getString(mag_idx);
			String place = cursor.getString(place_idx);
			
			txtMagnitude.setText(magnitude);
			txtPlace.setText(place);
			Log.d(TAG, place);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}
}
