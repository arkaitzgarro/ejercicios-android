package com.arkaitzgarro.earthquake;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.starky.earthquake.R;

import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;

public class EarthquakeListFragment extends ListFragment {

	private static final String TAG = "EARTHQUAKE";
	
	private MainActivity earthquakeActivity;
	
	private EarthquakeDB dbHelper;

	private ArrayAdapter<Quake> aa;
	private ArrayList<Quake> earthquakes = new ArrayList<Quake>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// Saltarse la restriccion de conexiones en el Thread principal
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onActivityCreated(savedInstanceState);
		
		earthquakeActivity = (MainActivity)getActivity();
		dbHelper = new EarthquakeDB(earthquakeActivity.getApplicationContext());
		dbHelper.open();

		aa = new ArrayAdapter<Quake>(getActivity(),
				android.R.layout.simple_list_item_1, earthquakes);
		setListAdapter(aa);
		
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		db.delete(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, null, null);
		
		refreshEarthquakes();

//		Thread t = new Thread(new Runnable() {
//			public void run() {
//				refreshEarthquakes();
//			}
//		});
//		t.start();
	}

	private void addNewQuake(Quake _quake) {
		//if (_quake.getMagnitude() > earthquakeActivity.getMinimumMagnitude()) {
			// Add the new quake to our list of earthquakes.
			earthquakes.add(_quake);
			
			// Notify the array adapter of a change.
			aa.notifyDataSetChanged();
			
			ContentValues newValues = new ContentValues();
			newValues.put(EarthquakeDB.KEY_DATE, _quake.getDate().getTime());
			newValues.put(EarthquakeDB.KEY_DETAILS, _quake.getDetails());
			newValues.put(EarthquakeDB.KEY_SUMMARY, _quake.toString());
			newValues.put(EarthquakeDB.KEY_LOCATION_LAT, _quake.getLocation().getLatitude());
			newValues.put(EarthquakeDB.KEY_LOCATION_LNG, _quake.getLocation().getLongitude());
			newValues.put(EarthquakeDB.KEY_MAGNITUDE, _quake.getMagnitude());
			newValues.put(EarthquakeDB.KEY_LINK, _quake.getLink());
			
			dbHelper.createRow(newValues);
			
			// Actualizar el campo de ultimo filtro
			earthquakeActivity.setLastUpdated();
		//}
	}
	
	public void refreshEarthquakes() {
		Date epoch = new Date(0);
		if(epoch.compareTo(earthquakeActivity.getLastUpdated()) == 0) {
			// No hay BD
			getEarthquakes();
		} else {
			// Filtrar los que hay en la BD
			filterEarthquakes();
		}
	}
	
	private void filterEarthquakes() {
		earthquakes.clear();
		Cursor c = dbHelper.query(EarthquakeDB.KEYS_ALL,
				EarthquakeDB.KEY_MAGNITUDE + ">" + earthquakeActivity.getMinimumMagnitude(),
				null, null, null, null);
		
		int KEY_DATE_IDX = c.getColumnIndex(EarthquakeDB.KEY_DATE);
		int KEY_DETAILS_IDX = c.getColumnIndex(EarthquakeDB.KEY_DETAILS);
		int KEY_LAT_IDX = c.getColumnIndex(EarthquakeDB.KEY_LOCATION_LAT);
		int KEY_LNG_IDX = c.getColumnIndex(EarthquakeDB.KEY_LOCATION_LNG);
		int KEY_MAGNITUDE_IDX = c.getColumnIndex(EarthquakeDB.KEY_MAGNITUDE);
		int KEY_LINK_IDX = c.getColumnIndex(EarthquakeDB.KEY_LINK);
		
		Log.i("DB", String.valueOf(c.getCount()));
		
		while(c.moveToNext()) {
			Log.i("CURSOR", String.valueOf(c.getFloat(KEY_DATE_IDX)));
			Date qdate = new Date(c.getLong(KEY_DATE_IDX));
			
			Location l = new Location("dummyGPS");
			l.setLatitude(c.getFloat(KEY_LAT_IDX));
			l.setLongitude(c.getFloat(KEY_LNG_IDX));
			
			Quake _quake = new Quake(qdate,
					c.getString(KEY_DETAILS_IDX),
					l,
					c.getFloat(KEY_MAGNITUDE_IDX),
					c.getString(KEY_LINK_IDX));
			
			earthquakes.add(_quake);
		}
		aa.notifyDataSetChanged();
		
		c.close();
	}

	private void getEarthquakes() {
		// Get the XML
		URL url;
		try {
			String quakeFeed = getString(R.string.quake_feed);
			url = new URL(quakeFeed);

			URLConnection connection;
			connection = url.openConnection();

			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				// Parse the earthquake feed.
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();

				// Clear the old earthquakes
				earthquakes.clear();

				// Get a list of each earthquake entry.
				NodeList nl = docEle.getElementsByTagName("entry");
				if (nl != null && nl.getLength() > 0) {
					for (int i = 0; i < nl.getLength(); i++) {
						Element entry = (Element) nl.item(i);
						Element title = (Element) entry.getElementsByTagName(
								"title").item(0);
						Element g = (Element) entry.getElementsByTagName(
								"georss:point").item(0);
						Element when = (Element) entry.getElementsByTagName(
								"updated").item(0);
						Element link = (Element) entry.getElementsByTagName(
								"link").item(0);

						String details = title.getFirstChild().getNodeValue();
						String hostname = "http://earthquake.usgs.gov";
						String linkString = hostname
								+ link.getAttribute("href");

						String point = g.getFirstChild().getNodeValue();
						String dt = when.getFirstChild().getNodeValue();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd'T'hh:mm:ss'Z'");
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
						double magnitude = Double.parseDouble(magnitudeString
								.substring(0, end));

						details = details.split(",")[1].trim();

						Quake quake = new Quake(qdate, details, l, magnitude,
								linkString);

						// Process a newly found earthquake
						addNewQuake(quake);
					}
				}
			}
		} catch (MalformedURLException e) {
			Log.d(TAG, "MalformedURLException", e);
		} catch (IOException e) {
			Log.d(TAG, "IOException", e);
		} catch (ParserConfigurationException e) {
			Log.d(TAG, "Parser Configuration Exception", e);
		} catch (SAXException e) {
			Log.d(TAG, "SAX Exception", e);
		} finally {
			Log.d(TAG, "Fatal error");
		}
	}
}