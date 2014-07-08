package com.example.groupmelocator.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alarm extends BroadcastReceiver
{
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onReceive(Context context, Intent intent)
    {

        // first, pull the stored location from memory
        // don't bother using SQL, just use persistent data

        double lat_stored, long_stored;
        try {

            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            long_stored = settings.getFloat("long", 0);
            lat_stored = settings.getFloat("lat", 0);

        }catch (Exception e) {
            // failure obtaining stored locations, initialize to 0
            lat_stored = 0;
            long_stored = 0;
        }

        // now do the main work
        try {
            // get all of the saved database locations
            MyDatabaseHandler db = new MyDatabaseHandler(context);
            db.open();
            List<MyLocation> locs = db.getAllLocations();
            db.close();

            // initialize a list of locations based on the DB
            // and a groupme communicator to post to groupme
            List<LocationState> locations = new ArrayList<LocationState>();
            GroupmeCom gc = new GroupmeCom();

            // go through the list of saved locations, see if the user
            // is currently in any of those locations
            if (locs != null && !locs.isEmpty()) {
                for (int i = 0; i < locs.size(); i++) {
                    MyLocation loc = locs.get(i);
                    locations.add(new LocationState(loc));
                    gc.PostMessage(loc.toString());
                }
            }

            // get the current location from best location
            MyLocation currLoc = GetLocationHandler.getCurrentLocation((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));

            // save this new location as the current location in persistent data for next run
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putFloat("long", (float) currLoc.getLongitude());
            editor.putFloat("lat", (float) currLoc.getLatitude());
            editor.commit();

            // TODO - REMOVE -- DEBUGGING INFO
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            gc.PostMessage(dateFormat.format(date) + " at " + currLoc.getLatitude() + ", " + currLoc.getLongitude());

            // go through the list of saved locations, see if the user
            // is currently in any of those locations
            if (locations != null && !locations.isEmpty()) {
                for (int i = 0; i < locations.size(); i++) {
                    LocationState loc = locations.get(i);

                    // if you are IN location and you are marked as not in location
                    // that means you entered location
                    if (loc.getMyLocation().IsPointInLocation(currLoc) && !loc.getIsInLoc()) {
                        gc.PostMessage(loc.getMyLocation().EnterMessage());

                        // set that we are now in this location
                        loc.setIsInLoc(true);
                        Log.i("onHandleIntent", "EnterMessage");
                    } else if (!loc.getMyLocation().IsPointInLocation(currLoc) && loc.getIsInLoc()) {
                        // we were listed as in this location but are not, that means we left

                        gc.PostMessage(loc.getMyLocation().LeftMessage());

                        // set that we are now in this location
                        loc.setIsInLoc(false);
                        Log.i("onHandleIntent", "LeftMessage");
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e("onHandleIntent", e.getMessage());
        }
    }
}