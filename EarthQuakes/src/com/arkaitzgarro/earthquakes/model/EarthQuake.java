package com.arkaitzgarro.earthquakes.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.location.Location;

public class EarthQuake {

	private long _id;
	private String idStr;
	private String place;
	private Date time;
	// private String detail;
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
		// this.detail = detail;
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

	public void setTime(Date time) {
		this.time = time;
	}

	// public String getDetail() {
	// return detail;
	// }
	//
	// public void setDetail(String detail) {
	// this.detail = detail;
	// }

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
