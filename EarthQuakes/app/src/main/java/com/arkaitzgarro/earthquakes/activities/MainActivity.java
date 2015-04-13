package com.arkaitzgarro.earthquakes.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakesListFragment;
import com.arkaitzgarro.earthquakes.fragments.EarthQuakesListMapFragment;
import com.arkaitzgarro.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.arkaitzgarro.earthquakes.listeners.TabListener;
import com.arkaitzgarro.earthquakes.managers.EarthQuakeAlarmManager;
import com.arkaitzgarro.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends Activity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    private final String EARTHQUAKE_PREFS = "EARTHQUAKE_PREFS";
    private final String SELECTED_TAB = "SELECTED_TAB";

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTabs();
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

    private void createTabs() {
        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabList = actionBar.newTab();

        tabList.setText(getString(R.string.tab_list_title))
                .setTabListener(
                        new TabListener<EarthQuakesListFragment>
                                (this, R.id.fragmentContainer, EarthQuakesListFragment.class));

        actionBar.addTab(tabList);

        ActionBar.Tab tabMap = actionBar.newTab();

        tabMap.setText(getString(R.string.tab_map_title))
                .setTabListener(
                        new TabListener<EarthQuakesListMapFragment>
                                (this, R.id.fragmentContainer, EarthQuakesListMapFragment.class));

        actionBar.addTab(tabMap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB, 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_TAB, actionBar.getSelectedNavigationIndex());

        super.onSaveInstanceState(outState);
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
