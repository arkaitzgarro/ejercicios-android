package com.arkaitzgarro.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.database.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakeListFragment;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakesMapFragment;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DetailAtivity extends ActionBarActivity {

    private final String DETAIL = "DETAIL";

    private EarthQuakeDB earthQuakeDB;

    private EarthQuakesMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ativity);

        mapFragment = (EarthQuakesMapFragment)getFragmentManager().findFragmentById(R.id.map_fragment);

        earthQuakeDB = new EarthQuakeDB(this);

        Intent detailIntent = getIntent();
        String id = detailIntent.getStringExtra(EarthQuakeListFragment.ID);

        EarthQuake earthQuake = earthQuakeDB.getEarthQuake(id);

        showEarthQuake(earthQuake);
    }

    private void showEarthQuake(EarthQuake earthQuake) {
        Log.d(DETAIL, earthQuake.getId());

        TextView lblMagnitude = (TextView) findViewById(R.id.lblMag);
        TextView lblPlace = (TextView) findViewById(R.id.lblPlace);
        TextView lblDate = (TextView) findViewById(R.id.lblDate);
        TextView lblUrl = (TextView) findViewById(R.id.lblUrl);

        lblMagnitude.setText(earthQuake.getMagnitudeFormated());
        lblPlace.setText(earthQuake.getPlace());
        lblDate.setText(earthQuake.getTimeFormated());
        lblUrl.setText(earthQuake.getUrl());

        showMap(earthQuake);
    }

    private void showMap(EarthQuake earthQuake) {
        List<EarthQuake> earthQuakes = new ArrayList<>();
        earthQuakes.add(earthQuake);

        mapFragment.setEarthQuakes(earthQuakes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivity(prefsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
