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
}
