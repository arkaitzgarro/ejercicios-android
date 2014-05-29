package com.arkaitzgarro.maps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends android.support.v4.app.FragmentActivity {

	private LocationManager locationManager;
	private GoogleMap mMap;

	private TextView longitude;
	private TextView latitude;
	private TextView altitude;

	private Button btnRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout._activity_main);

		longitude = (TextView) findViewById(R.id.longitude);
		latitude = (TextView) findViewById(R.id.latitude);
		altitude = (TextView) findViewById(R.id.altitude);

		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getLastKnownLocation();
			}
		});

		// setUpMapIfNeeded();

		String serviceString = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(serviceString);

		getLastKnownLocation();
		trackPosition();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// MarkerOptions marker = new MarkerOptions().position(new LatLng(0,
		// 0)).title("Marker");
		// mMap.addMarker(marker);

		LatLng madrid = new LatLng(40.417325, -3.683081);
		CameraPosition camPos = new CameraPosition.Builder().target(madrid)
				.zoom(19).bearing(45).tilt(70).build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);

		mMap.animateCamera(camUpd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private String getBestProvider() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setSpeedRequired(false);

		// return locationManager.getBestProvider(criteria, true);
		return LocationManager.GPS_PROVIDER;
	}

	private void getLastKnownLocation() {

		String bestProvider = getBestProvider();

		Location location = locationManager.getLastKnownLocation(bestProvider);

		if (location != null) {
			Log.i("GPS", location.toString());
			updateViews(location);
		} else {
			Log.i("GPS", "No position");
			Toast t = Toast.makeText(this, "No last known location",
					Toast.LENGTH_LONG);
			t.show();
		}
	}

	private void trackPosition() {
		String bestProvider = getBestProvider();

		int t = 5000; // milliseconds
		int distance = 5; // meters

		LocationListener locListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Log.i("GPS", location.toString());
				updateViews(location);
			}

			public void onProviderDisabled(String provider) {
				// Update application if provider disabled.
			}

			public void onProviderEnabled(String provider) {
				// Update application if provider enabled.
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// Update application if provider hardware status changed.
			}
		};
		locationManager.requestLocationUpdates(bestProvider, t, distance,
				locListener);
	}

	private void updateViews(Location location) {
		latitude.setText(String.valueOf(location.getLatitude()));
		longitude.setText(String.valueOf(location.getLongitude()));
		altitude.setText(String.valueOf(location.getAltitude()));
	}

}
