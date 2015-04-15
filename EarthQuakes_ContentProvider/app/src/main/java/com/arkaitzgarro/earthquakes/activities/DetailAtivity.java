package com.arkaitzgarro.earthquakes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.providers.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakesListFragment;
import com.arkaitzgarro.earthquakes.model.EarthQuake;

public class DetailAtivity extends Activity {

    private final String DETAIL = "DETAIL";

    private EarthQuakeDB earthQuakeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ativity);

        earthQuakeDB = new EarthQuakeDB(this);

        Intent detailIntent = getIntent();
        String id = detailIntent.getStringExtra(EarthQuakesListFragment.ID);

        //EarthQuake earthQuake = earthQuakeDB.getEarthQuake(id);

        //showDetails(earthQuake);
    }

    private void showDetails(EarthQuake earthQuake) {
        Log.d(DETAIL, earthQuake.getId());

        TextView lblMagnitude = (TextView) findViewById(R.id.lblMag);
        TextView lblPlace = (TextView) findViewById(R.id.lblPlace);
        TextView lblDate = (TextView) findViewById(R.id.lblDate);
        TextView lblUrl = (TextView) findViewById(R.id.lblUrl);

        lblMagnitude.setText(earthQuake.getMagnitudeFormated());
        lblPlace.setText(earthQuake.getPlace());
        lblDate.setText(earthQuake.getTimeFormated());
        lblUrl.setText(earthQuake.getUrl());
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
