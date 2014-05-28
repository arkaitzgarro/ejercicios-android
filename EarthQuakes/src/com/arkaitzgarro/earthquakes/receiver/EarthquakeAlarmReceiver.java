package com.arkaitzgarro.earthquakes.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.arkaitzgarro.earthquakes.service.UpdateEarthQuakes;

public class EarthquakeAlarmReceiver extends BroadcastReceiver {

	public static final String ACTION_REFRESH_EARTHQUAKE_ALARM = "com.arkaitzgarro.earthquakes.ACTION_REFRESH_EARTHQUAKE_ALARM";

	private static final String TAG = "EARTHQUAKE";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "ALARM RECEIVER: onReceive()");
		
		Intent startIntent = new Intent(context, UpdateEarthQuakes.class);
		context.startService(startIntent);
	}

}
