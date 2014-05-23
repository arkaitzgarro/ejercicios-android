package com.starky.earthquake;

import java.text.SimpleDateFormat;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EarthquakeViewBinder implements SimpleCursorAdapter.ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		int date = cursor.getColumnIndex(EarthquakeProvider.KEY_DATE);
		
        if (date == columnIndex) {
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String dateString = sdf.format(cursor.getLong(date));
            
            ((TextView) view).setText(dateString);
            
            return true;
        }
        
        return false;
	}



}
