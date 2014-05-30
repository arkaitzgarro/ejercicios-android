package com.arkaitzgarro.earthquakes.fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;

public class EarthquakeViewBinder implements SimpleCursorAdapter.ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		int time_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_TIME);
		int mag_idx = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_MAGNITUDE);
		
		TextView v = ((TextView) view);
		
        if (time_idx == columnIndex) {
        	SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
            String dateString = sdf.format(cursor.getLong(time_idx));
            
            v.setText(dateString);
            
            return true;
        } else if(mag_idx == columnIndex) {
        	double mag = cursor.getDouble(mag_idx);
        	
        	// Gradient from green to red
        	int red = (int)(255 * mag * 10)/100;
        	int green = (int)(255*(100 - mag * 10))/100; 
        	int blue = 0;
        	v.setBackgroundColor(Color.rgb(red, green, blue));
        	
        	DecimalFormat df = new DecimalFormat("0.00");
        	v.setText(df.format(mag));
        	
        	return true;
        }
        
        return false;
	}

}
