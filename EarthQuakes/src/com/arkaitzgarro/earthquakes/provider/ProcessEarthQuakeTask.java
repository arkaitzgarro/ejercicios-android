package com.arkaitzgarro.earthquakes.provider;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.arkaitzgarro.earthquakes.model.EarthQuake;

public class ProcessEarthQuakeTask extends AsyncTask<JSONObject, Void, EarthQuake> {

	private static final String TAG = "EARTHQUAKE";

//	private EarthQuakeDB db;
	
	private Context mContext;

	public ProcessEarthQuakeTask(Context context) {
		mContext = context;
//		db = EarthQuakeDB.getDB((Context)mContext);
	}

	@Override
	protected EarthQuake doInBackground(JSONObject... json) {
		JSONObject e = json[0];
		
		try {
			JSONObject p = e.getJSONObject("properties");
			JSONArray l = e.getJSONObject("geometry").getJSONArray(
					"coordinates");

			EarthQuake q = new EarthQuake();
			q.setIdStr(e.getString("id"));
			q.setPlace(p.getString("place"));
			q.setMagnitude(p.getDouble("mag"));
			q.setTime(new Date(p.getLong("time")));
			q.setUrl(p.getString("url"));
			q.setLongitude(l.getDouble(0));
			q.setLatitude(l.getDouble(1));
			
			this.insertEarthQuake(q);

			return q;

		} catch (JSONException ex) {
			Log.e("EARTHQUAKE", ex.getMessage());
		}
		
		return null;
	}
	
	private void insertEarthQuake(EarthQuake q) {
		ContentResolver cr = mContext.getContentResolver();
		ContentValues newValues = new ContentValues();

		newValues.put(EarthQuakeProvider.Columns.KEY_TIME, q.getTime()
				.getTime());
		newValues
				.put(EarthQuakeProvider.Columns.KEY_ID_STR, q.getIdStr());
		newValues.put(EarthQuakeProvider.Columns.KEY_PLACE, q.getPlace());
		newValues.put(EarthQuakeProvider.Columns.KEY_LOCATION_LAT, q
				.getLocation().getLatitude());
		newValues.put(EarthQuakeProvider.Columns.KEY_LOCATION_LNG, q
				.getLocation().getLongitude());
		newValues.put(EarthQuakeProvider.Columns.KEY_MAGNITUDE,
				q.getMagnitude());
		newValues.put(EarthQuakeProvider.Columns.KEY_URL, q.getUrl());
		
		cr.insert(EarthQuakeProvider.CONTENT_URI, newValues);
	}

	protected void onPostExecute(EarthQuake q) {
		Log.d(TAG, "SENT: " + q);
	}
}
