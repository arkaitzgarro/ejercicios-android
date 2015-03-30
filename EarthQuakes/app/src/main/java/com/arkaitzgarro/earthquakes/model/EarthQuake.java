package com.arkaitzgarro.earthquakes.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arkaitz on 25/03/15.
 */
public class EarthQuake {

    private String _id;

    private String place;

    private Date time;

    private Coordinate coords;

    private double magnitude;

    private String url;

    public EarthQuake() {
        this.coords = new Coordinate(0.0, 0.0, 0.0);
    }

    public EarthQuake(String _id, String place, Date time, Coordinate coords, double magnitude) {
        this._id = _id;
        this.place = place;
        this.time = time;
        this.coords = coords;
        this.magnitude = magnitude;
    }


    @Override
    public String toString() {
        return place;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");

        return sdf.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = new Date(time);
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getMagnitudeFormated() {
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(magnitude);
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLatitude(double latitude) {
        this.coords.setLat(latitude);
    }

    public void setLongitude(double longitude) {
        this.coords.setLng(longitude);
    }

    public void setDepth(double depth) {
        this.coords.setDepth(depth);
    }
}
