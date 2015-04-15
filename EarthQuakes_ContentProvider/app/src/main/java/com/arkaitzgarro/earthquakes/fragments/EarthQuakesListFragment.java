package com.arkaitzgarro.earthquakes.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activities.DetailAtivity;
import com.arkaitzgarro.earthquakes.adapters.EarthQuakeAdapter;
import com.arkaitzgarro.earthquakes.providers.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.providers.EarthQuakesProvider;
import com.arkaitzgarro.earthquakes.services.DownloadEarthquakesService;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakesListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID = "_id";

    private static final int LOADER_ID = 1;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    private String[] from = { EarthQuakesProvider.Columns.KEY_DATE,
            EarthQuakesProvider.Columns.KEY_MAGNITUDE,
            EarthQuakesProvider.Columns.KEY_PLACE,
            EarthQuakesProvider.Columns._ID };
    private int[] to = { R.id.lblDate, R.id.lblMag, R.id.lblPlace };
    private SimpleCursorAdapter adapter;

    private SharedPreferences prefs;
    private EarthQuakeDB earthQuakeDB;

    private List<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        earthQuakeDB = new EarthQuakeDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        earthQuakes = new ArrayList<>();
        //aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        //setListAdapter(aa);
        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.earthquake_item, null, from, to, 0);
        setListAdapter(adapter);

        mCallbacks = this;

        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*int minMag = Integer.parseInt(prefs.getString(getString(R.string.PREF_MIN_MAG), "0"));

        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.getAllByMagnitude(minMag));

        aa.notifyDataSetChanged();*/

        getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailAtivity.class);
        detailIntent.putExtra(ID, earthQuakes.get(position).getId());

        startActivity(detailIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Intent download = new Intent(getActivity(), DownloadEarthquakesService.class);
            getActivity().startService(download);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String minMag = prefs.getString(
                getResources().getString(R.string.PREF_MIN_MAG), "0");

        String where = EarthQuakesProvider.Columns.KEY_MAGNITUDE + " >= ? ";
        String[] whereArgs = { minMag };
        String order = EarthQuakesProvider.Columns.KEY_DATE + " DESC";

        CursorLoader loader = new CursorLoader(getActivity(),
                EarthQuakesProvider.CONTENT_URI, from, where, whereArgs, order);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
