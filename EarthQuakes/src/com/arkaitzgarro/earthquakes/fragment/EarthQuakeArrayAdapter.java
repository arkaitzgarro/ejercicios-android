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

	static class ViewHolder {
		public TextView magnitude;
		public TextView place;
		public TextView time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.earthquake_row_layout, null);

			// Configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.magnitude = (TextView) rowView.findViewById(R.id.magnitude);
			viewHolder.place = (TextView) rowView.findViewById(R.id.place);
			viewHolder.time = (TextView) rowView.findViewById(R.id.time);
			
			rowView.setTag(viewHolder);
		}

		// Fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.magnitude.setText(String.valueOf(values.get(position).getMagnitude()));
		holder.place.setText(String.valueOf(values.get(position).getPlace()));
		holder.time.setText(String.valueOf(values.get(position).getTimeFormated()));

		return rowView;
	}
}
