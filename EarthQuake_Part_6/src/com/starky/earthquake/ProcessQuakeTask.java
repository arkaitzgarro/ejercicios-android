package com.starky.earthquake;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.w3c.dom.Element;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class ProcessQuakeTask extends AsyncTask<Element, Void, Void> {

	private static final String TAG = "EARTHQUAKE";

	private MainActivity mContext;

	public ProcessQuakeTask(MainActivity context) {
		mContext = context;
	}

	@Override
	protected Void doInBackground(Element... arg0) {
		Log.i(TAG, "ProcessQuakeTask.doInBackground()");
		
		// Get the XML
		Element entry = arg0[0];

		Element title = (Element) entry.getElementsByTagName("title").item(0);
		Element g = (Element) entry.getElementsByTagName("georss:point")
				.item(0);
		Element when = (Element) entry.getElementsByTagName("updated").item(0);
		Element link = (Element) entry.getElementsByTagName("link").item(0);

		String details = title.getFirstChild().getNodeValue();
		String hostname = "http://earthquake.usgs.gov";
		String linkString = hostname + link.getAttribute("href");

		String point = g.getFirstChild().getNodeValue();
		String dt = when.getFirstChild().getNodeValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		Date qdate = new GregorianCalendar(0, 0, 0).getTime();
		try {
			qdate = sdf.parse(dt);
		} catch (ParseException e) {
			Log.d(TAG, "Date parsing exception.", e);
		}

		String[] location = point.split(" ");
		Location l = new Location("dummyGPS");
		l.setLatitude(Double.parseDouble(location[0]));
		l.setLongitude(Double.parseDouble(location[1]));

		String magnitudeString = details.split(" ")[1];
		int end = magnitudeString.length() - 1;
		double magnitude = Double
				.parseDouble(magnitudeString.substring(0, end));

		details = details.split(",")[1].trim();

		Quake quake = new Quake(qdate, details, l, magnitude, linkString);

		addNewQuake(quake);
		
		return null;
	}
	
	private void addNewQuake(Quake quake) {
		Log.i(TAG, "ProcessQuakeTask.addNewQuake()");
		
	    ContentResolver cr = mContext.getContentResolver();

	    // Construct a where clause to make sure we don't already have this
	    // earthquake in the provider.
	    String w = EarthquakeProvider.KEY_DATE + " = " + quake.getDate().getTime();

	    // If the earthquake is new, insert it into the provider.
	    Cursor query = cr.query(EarthquakeProvider.CONTENT_URI, null, w, null, null);
	    
	    if (query.getCount()==0) {
	      ContentValues values = new ContentValues();

	      values.put(EarthquakeProvider.KEY_DATE, quake.getDate().getTime());
	      values.put(EarthquakeProvider.KEY_DETAILS, quake.getDetails());   
	      values.put(EarthquakeProvider.KEY_SUMMARY, quake.toString());

	      double lat = quake.getLocation().getLatitude();
	      double lng = quake.getLocation().getLongitude();
	      values.put(EarthquakeProvider.KEY_LOCATION_LAT, lat);
	      values.put(EarthquakeProvider.KEY_LOCATION_LNG, lng);
	      values.put(EarthquakeProvider.KEY_LINK, quake.getLink());
	      values.put(EarthquakeProvider.KEY_MAGNITUDE, quake.getMagnitude());

	      cr.insert(EarthquakeProvider.CONTENT_URI, values);
	      Log.i(TAG, "ProcessQuakeTask.addNewQuake(): INSERTED");
	    } else {
	    	Log.i(TAG, "ProcessQuakeTask.addNewQuake(): EXISTS");
	    }
	    
	    query.close();
	  }
}
