package com.arkaitzgarro.maps;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMarkerClickListener {
	
	private final String TAG = "MAP";
	private GoogleMap map;
	
	private HashMap<Marker, String> earthQuakeMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		earthQuakeMarkers = new HashMap<Marker, String>();
		
		getMap();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(map == null) {
			getMap();
		}
		
		MarkerOptions marker = new MarkerOptions()
			.position(new LatLng(0, 0));
	
		Marker m = map.addMarker(marker);
		earthQuakeMarkers.put(m, "ID1");
	}
	
	private void getMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setOnMarkerClickListener(this);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		String id = earthQuakeMarkers.get(marker);
		Log.d(TAG, id);
		
		return false;
	}

}
