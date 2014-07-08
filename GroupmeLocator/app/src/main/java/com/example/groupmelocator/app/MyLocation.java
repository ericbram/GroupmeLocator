package com.example.groupmelocator.app;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by Eric on 6/5/2014.
 *
 *
 * used as a template:
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
public class MyLocation implements Serializable{
    private long id;
    private String locationname;
    private double latitude;
    private double longitude;
    private double radius;

    public boolean IsPointInLocation(MyLocation loc) {
        float[] results = new float[1];

        // get the distance between the current point and the desired point
        Location.distanceBetween(this.latitude, this.longitude, loc.getLatitude(), loc.getLongitude(), results);

        // calculate the results in miles
        float meters = results[0];
        double miles = meters * 0.000621371;

        // if the distance is less than or equal to the radius specified, you are in that area
        return miles <= radius;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationname;
    }

    public void setLocationName(String locationname) {
        this.locationname = locationname;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return locationname + " is at " + latitude + "," + longitude + " with a radius of " + radius + ".";
    }

    public String EnterMessage() {
        return "Eric has arrived at " + locationname + ".";
    }

    public String LeftMessage() {
        return "Eric has left " + locationname + ".";
    }
}
