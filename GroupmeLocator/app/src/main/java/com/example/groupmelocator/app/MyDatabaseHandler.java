package com.example.groupmelocator.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Eric on 6/5/2014.
 *
 * used as a template:
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
public class MyDatabaseHandler {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_LOCATIONNAME, MySQLiteHelper.COLUMN_LONGITUDE,
            MySQLiteHelper.COLUMN_LATITUDE, MySQLiteHelper.COLUMN_RADIUS};

    public MyDatabaseHandler(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MyLocation createMyLocation(String name, double latitude, double longitude, double radius) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_LOCATIONNAME, name);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);
        values.put(MySQLiteHelper.COLUMN_RADIUS, radius);
        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MyLocation newLocation = cursorToNewLocation(cursor);
        cursor.close();
        return newLocation;
    }

    public void deleteLocation(MyLocation location) {
        long id = location.getId();
        System.out.println("location deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<MyLocation> getAllLocations() {
        List<MyLocation> locs = new ArrayList<MyLocation>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyLocation loc = cursorToNewLocation(cursor);
            locs.add(loc);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return locs;
    }

    private MyLocation cursorToNewLocation(Cursor cursor) {
        MyLocation loc = new MyLocation();
        loc.setId(cursor.getLong(0));
        loc.setLocationName(cursor.getString(1));
        loc.setLatitude(cursor.getDouble(3));
        loc.setLongitude(cursor.getDouble(2));
        loc.setRadius(cursor.getDouble(4));
        return loc;
    }
}
