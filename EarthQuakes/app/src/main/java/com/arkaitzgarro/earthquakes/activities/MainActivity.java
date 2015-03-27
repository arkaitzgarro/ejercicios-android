package com.arkaitzgarro.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadEarthQuakes();
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

    private void downloadEarthQuakes() {
        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this, this);
        task.execute(getString(R.string.earthquakes_url));
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);

        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
