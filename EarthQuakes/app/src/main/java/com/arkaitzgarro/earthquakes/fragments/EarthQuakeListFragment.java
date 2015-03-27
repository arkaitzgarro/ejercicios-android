package com.arkaitzgarro.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activities.DetailAtivity;
import com.arkaitzgarro.earthquakes.adapters.EarthQuakeAdapter;
import com.arkaitzgarro.earthquakes.model.EarthQuake;

import java.util.ArrayList;

/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakeListFragment extends ListFragment {

    public static final String ID = "_id";

    private SharedPreferences prefs;

    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailAtivity.class);
        detailIntent.putExtra(ID, earthQuakes.get(position).getId());

        startActivity(detailIntent);
    }
}
