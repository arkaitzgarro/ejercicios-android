package com.starky.earthquake;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.w3c.dom.Element;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.starky.earthquake.UpdateQuakesTask.IUpdateQuakes;

public class ProcessQuakeTask extends AsyncTask<Element, Void, Quake> {

	private static final String TAG = "EARTHQUAKE";

	private IUpdateQuakes mContext;

	public ProcessQuakeTask(IUpdateQuakes context) {
		mContext = context;
	}

	@Override
	protected Quake doInBackground(Element... arg0) {
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

		return quake;
	}

	protected void onPostExecute(Quake q) {
		mContext.addQuake(q);
		Log.i(TAG, "Enviado " + q.getDetails());
	}
}
