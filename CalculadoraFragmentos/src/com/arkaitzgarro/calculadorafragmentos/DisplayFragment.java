package com.arkaitzgarro.calculadorafragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {

	private TextView resultView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.display_fragment, container, false);

		resultView = (TextView)view.findViewById(R.id.result);

		return view;
	}
	
	public void updateDisplay(String result) {
		resultView.setText(result);
	}

}
