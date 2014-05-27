package com.arkaitzgarro.earthquakes.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.arkaitzgarro.earthquakes.provider.EarthQuakeProvider;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EarthquakeViewBinder implements SimpleCursorAdapter.ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		int time = cursor.getColumnIndex(EarthQuakeProvider.Columns.KEY_TIME);
		
        if (time == columnIndex) {
        	SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
            String dateString = sdf.format(cursor.getLong(time));
            
            ((TextView) view).setText(dateString);
            
            return true;
        }
        
        return false;
	}

}
