package com.arkaitzgarro.earthquakes.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.location.Location;

public class EarthQuake implements Serializable {

	private static final long serialVersionUID = -8534022271629673915L;

	private long _id;
	private String idStr;
	private String place;
	private Date time;
	private double magnitude;
	private Location location;
	private String url;

	public EarthQuake() {
		super();
		
		this.location = new Location("Location");
	}

	public EarthQuake(String idStr, String place, long timestamp, double magnitude,
			double latitude, double longitude, String url) {
		this();

		this.idStr = idStr;
		this.place = place;
		this.time = new Date(timestamp);
		this.magnitude = magnitude;
		this.url = url;

		this.location = new Location(place);
		this.location.setLatitude(latitude);
		this.location.setLongitude(longitude);
	}
	
	public long getId() {
		return _id;
	}

	public void setId(long id) {
		this._id = id;
	}
	
	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getTime() {
		return time;
	}
	
	public String getTimeFormated() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
		return sdf.format(time);
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setLatitude(double lat) {
		this.location.setLatitude(lat);
	}
	
	public void setLongitude(double lng) {
		this.location.setLongitude(lng);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm", Locale.ENGLISH);
		String dateString = sdf.format(time);
		DecimalFormat df = new DecimalFormat("#.##");
		
		return dateString + ": " + df.format(magnitude) + " " + place;
	}
}
