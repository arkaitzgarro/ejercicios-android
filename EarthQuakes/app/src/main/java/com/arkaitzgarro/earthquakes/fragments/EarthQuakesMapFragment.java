package com.arkaitzgarro.earthquakes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakesMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private List<EarthQuake> earthQuakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        map = getMap();
        map.setOnMapLoadedCallback(this);

        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthQuakes = earthQuakes;
    }

    @Override
    public void onMapLoaded() {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (earthQuakes.size() == 1) {
            showEarthQuakes(earthQuakes.get(0));
        } else {
            showEarthQuakes(earthQuakes);
        }
    }

    private void showEarthQuakes(EarthQuake earthQuake) {
        MarkerOptions marker = createMarker(earthQuake);

        map.addMarker(marker);

        CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition())
                .zoom(5)
                .build();

        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);

        map.animateCamera(camUpd);
    }

    private void showEarthQuakes(List<EarthQuake> earthQuakes) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (EarthQuake earthQuake: earthQuakes) {

            MarkerOptions marker = createMarker(earthQuake);

            map.addMarker(marker);
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);

        map.animateCamera(cu);
    }

    private MarkerOptions createMarker(EarthQuake earthQuake) {
        LatLng point = new LatLng(
                earthQuake.getCoords().getLng(),
                earthQuake.getCoords().getLat()
        );

        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title(earthQuake.getMagnitudeFormated().concat(" ").concat(earthQuake.getPlace()))
                .snippet(earthQuake.getCoords().toString());

        return marker;
    }
}
