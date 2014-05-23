package com.arkaitzgarro.calculadorafragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class KeyboardFragment extends Fragment {

	public interface ICalculator {
		public void setNumber(String n);
		public void setOp(String op);
	}
	
	ICalculator activity;
	
	View view;
	
	private TypedArray numbers;
	private TypedArray ops;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			this.activity = (ICalculator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ICalculator");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.keyboard_fragment, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		numbers = getResources().obtainTypedArray(R.array.numbers);
		ops = getResources().obtainTypedArray(R.array.ops);
		
		// Listener for number buttons
		View.OnClickListener numberListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button btn = (Button)v;
				activity.setNumber(btn.getText().toString());
			}
		};

		// Listener for op buttons
		View.OnClickListener opListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button btn = (Button)v;
				activity.setOp(btn.getText().toString());
			}
		};
		
		for(int i=0; i<numbers.length(); i++) {
			// Assign listener
			Log.d("CALC", String.valueOf(numbers.getResourceId(i, 0)));
			Button btn = (Button)view.findViewById(numbers.getResourceId(i, 0));
			btn.setOnClickListener(numberListener);
		}
		numbers.recycle();
		
		for(int i=0; i<ops.length(); i++) {
			// Assign listener
			Log.d("CALC", String.valueOf(ops.getResourceId(i, 0)));
			Button btn = (Button)view.findViewById(ops.getResourceId(i, 0));
			btn.setOnClickListener(opListener);
		}
		ops.recycle();
	}

}
