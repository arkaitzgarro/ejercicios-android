package com.arkaitzgarro.earthquakes.fragments;

import com.arkaitzgarro.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by arkaitz on 13/04/15.
 */
public class EarthQuakeMapFragment extends AbstractMapFragment {

    private EarthQuake earthQuake;

    @Override
    protected void getData() {
        String id = getActivity().getIntent().getStringExtra(EarthQuakesListFragment.ID);

        earthQuake = earthQuakeDB.getEarthQuake(id);
    }

    @Override
    protected void showMap() {
        MarkerOptions marker = createMarker(earthQuake);

        getMap().addMarker(marker);

        CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition())
                .zoom(5)
                .build();

        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);

        getMap().animateCamera(camUpd);
    }
}
