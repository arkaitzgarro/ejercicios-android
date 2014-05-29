package com.arkaitzgarro.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		String key = LocationManager.KEY_LOCATION_CHANGED;
		Location location = (Location) intent.getExtras().get(key);

		if (location != null) {
			Log.i("GPS", location.toString());
		}
	}

}
