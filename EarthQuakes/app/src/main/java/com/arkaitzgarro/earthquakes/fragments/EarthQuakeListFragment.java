package com.arkaitzgarro.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activities.DetailAtivity;
import com.arkaitzgarro.earthquakes.adapters.EarthQuakeAdapter;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.tasks.DownloadEarthquakesTask;

import java.util.ArrayList;

/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    public static final String ID = "_id";

    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();

        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this);
        task.execute(getString(R.string.earthquakes_url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailAtivity.class);
        detailIntent.putExtra(ID, earthQuakes.get(position).getId());

        startActivity(detailIntent);
    }

    @Override
    public void addEarthQuake(EarthQuake earthquake) {
        if (earthquake.getMagnitude() > 0) {
            earthQuakes.add(0, earthquake);
            aa.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);

        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
