package com.example.groupmelocator.app;

/**
 * Created by Eric on 6/5/2014.
 *
 *
 * used as a template:
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
public class MyLocation {
    private long id;
    private String locationname;
    private double latitude;
    private double longitude;
    private double radius;

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
        return "NEED THIS DONE -- TODO";
    }
}
