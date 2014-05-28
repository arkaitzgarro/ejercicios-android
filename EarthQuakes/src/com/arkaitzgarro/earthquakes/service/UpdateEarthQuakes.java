package com.arkaitzgarro.earthquakes.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;

public class UpdateEarthQuakes extends Service {
	
	public static final String URL = "url";
	
	private static final String TAG = "EARTHQUAKE";
	
	private String quake_feed;
	private Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d(TAG, "SERVICE onCreate()");
		
		mContext = UpdateEarthQuakes.this;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "SERVICE onStartCommand()");
		
		quake_feed = getResources().getString(R.string.quake_feed);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				downloadEarthQuakes(quake_feed);
			}
		});
		t.start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void downloadEarthQuakes(String quake_feed) {
		JSONObject json;
		URL url;
		
		try {
			url = new URL(quake_feed);
			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try {
					BufferedReader streamReader = new BufferedReader(
							new InputStreamReader(
									httpConnection.getInputStream(), "UTF-8"));
					StringBuilder responseStrBuilder = new StringBuilder();

					String inputStr;
					while ((inputStr = streamReader.readLine()) != null)
						responseStrBuilder.append(inputStr);

					json = new JSONObject(responseStrBuilder.toString());
					JSONArray earthquakes = json.getJSONArray("features");
					
					for (int i = earthquakes.length()-1; i >= 0; i--) {
						processEarthQuakeTask(earthquakes.getJSONObject(i));
					}
				} catch (JSONException e) {
					Log.e(TAG,
							"Error al leer el fichero JSON: " + e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			Log.d(TAG, "Malformed URL Exception.", e);
		} catch (IOException e) {
			Log.d(TAG, "IO Exception.", e);
		} finally {
			stopSelf();
		}
	}
	
	private void processEarthQuakeTask(JSONObject e) {
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
			
			insertEarthQuake(q);

		} catch (JSONException ex) {
			Log.e(TAG, ex.getMessage());
		}
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.d(TAG, "SERVICE onDestroy()");
	}

}
