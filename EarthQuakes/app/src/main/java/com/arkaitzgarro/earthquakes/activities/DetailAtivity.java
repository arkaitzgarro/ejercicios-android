package com.arkaitzgarro.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakeListFragment;

public class DetailAtivity extends ActionBarActivity {

    private final String DETAIL = "DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ativity);

        Intent detailIntent = getIntent();
        String id = detailIntent.getStringExtra(EarthQuakeListFragment.ID);

        Log.d(DETAIL, id);
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
