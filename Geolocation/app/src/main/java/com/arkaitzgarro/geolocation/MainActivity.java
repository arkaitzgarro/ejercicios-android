package com.arkaitzgarro.geolocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.arkaitzgarro.geolocation.listeners.LocationListener;


public class MainActivity extends ActionBarActivity implements LocationListener.AddLocationInterface {

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;

    private String provider;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager)getSystemService (Context.LOCATION_SERVICE);

        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude= (TextView) findViewById(R.id.lblLongitude);
        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblSpeed = (TextView) findViewById(R.id.lblSpeed);

        getLocationProvider();
        listenLocationChanges();
    }

    private void getLocationProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        provider = locationManager.getBestProvider(criteria, true);
    }

    private void listenLocationChanges() {
        int t = 5000;	// milliseconds
        int distance = 5; // meters

        LocationListener listener = new LocationListener(this);

        locationManager.requestLocationUpdates(provider, t, distance, listener);
    }

    @Override
    public void addLocation(Location location) {
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
        lblAltitude.setText(String.valueOf(location.getAltitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
    }
}
