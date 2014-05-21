package com.arkaitzgarro.earthquakes.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;

public class EarthQuakeArrayAdapter extends ArrayAdapter<EarthQuake> {
	private final Context context;
	private final ArrayList<EarthQuake> values;

	public EarthQuakeArrayAdapter(Context context, ArrayList<EarthQuake> values) {
		super(context, R.layout.earthquake_row_layout, values);

		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.earthquake_row_layout, parent, false);
		
		TextView magnitude = (TextView) rowView.findViewById(R.id.magnitude);
		TextView place = (TextView) rowView.findViewById(R.id.place);
		TextView time = (TextView) rowView.findViewById(R.id.time);
		
		magnitude.setText(String.valueOf(values.get(position).getMagnitude()));
		place.setText(values.get(position).getPlace());
		time.setText(values.get(position).getTimeFormated());

		return rowView;
	}
}
