package com.arkaitzgarro.earthquakes.activitiy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.fragment.EarthQuakeList;
import com.arkaitzgarro.earthquakes.fragment.EarthQuakeMap;
import com.arkaitzgarro.earthquakes.fragment.TabListener;
import com.arkaitzgarro.earthquakes.receiver.EarthquakeAlarmReceiver;

public class MainActivity extends Activity {

	private TabListener<EarthQuakeList> listTabListener;
	private TabListener<EarthQuakeMap> mapTabListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tabList = actionBar.newTab();
		listTabListener = new TabListener<EarthQuakeList>(this, R.id.container,
				EarthQuakeList.class);

		tabList.setText(getResources().getString(R.string.tabs_list))
				.setContentDescription(
						getResources().getString(R.string.tabs_list_desc))
				.setTabListener(listTabListener);

		actionBar.addTab(tabList);

		Tab tabMap = actionBar.newTab();
		mapTabListener = new TabListener<EarthQuakeMap>(this, R.id.container,
				EarthQuakeMap.class);

		tabMap.setText(getResources().getString(R.string.tabs_map))
				.setContentDescription(
						getResources().getString(R.string.tabs_map_desc))
				.setTabListener(mapTabListener);

		actionBar.addTab(tabMap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		} else if (id == R.id.action_refresh) {
			Intent i = new Intent(
					EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM);
			startService(i);
		}

		return super.onOptionsItemSelected(item);
	}

}
