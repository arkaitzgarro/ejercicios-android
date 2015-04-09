package com.arkaitzgarro.earthquakes.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.managers.EarthQuakeAlarmManager;
import com.arkaitzgarro.earthquakes.services.DownloadEarthquakesService;
import com.arkaitzgarro.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    private final String EARTHQUAKE_PREFS = "EARTHQUAKE_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkToSetAlarm();
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

    private void checkToSetAlarm() {
//        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this, this);
//        task.execute(getString(R.string.earthquakes_url));

//        Intent download = new Intent(this, DownloadEarthquakesService.class);
//        startService(download);

        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);

        if (!prefs.getBoolean(KEY, false)) {
            long interval = getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            EarthQuakeAlarmManager.setAlarm(this, interval);

            prefs.edit().putBoolean(KEY, true).apply();
        }

    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);

        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
