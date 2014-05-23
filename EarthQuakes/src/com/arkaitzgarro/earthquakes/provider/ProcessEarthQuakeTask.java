package com.arkaitzgarro.earthquakes.provider;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.UpdateEarthQuakesTask.IUpdateQuakes;

public class ProcessEarthQuakeTask extends AsyncTask<JSONObject, Void, EarthQuake> {

	private static final String TAG = "EARTHQUAKE";

	private EarthQuakeDB db;
	
	private IUpdateQuakes mContext;

	public ProcessEarthQuakeTask(IUpdateQuakes context) {
		mContext = context;
		db = EarthQuakeDB.getDB((Context)mContext);
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
			
			long id = db.insertEarthQuake(q);
			if(id != -1) {
				q.setId(id);
			}

			return q;

		} catch (JSONException ex) {
			Log.e("EARTHQUAKE", ex.getMessage());
		}
		
		return null;
	}

	protected void onPostExecute(EarthQuake q) {
		mContext.addQuake(q);
		Log.d(TAG, "SENT: " + q);
	}
}
