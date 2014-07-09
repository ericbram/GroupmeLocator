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
        String botID = "";
        MyLocation storedLoc = new MyLocation();
        try {

            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            long_stored = settings.getFloat("long", 0);
            lat_stored = settings.getFloat("lat", 0);
            botID = settings.getString("botid", "");

        }catch (Exception e) {
            // failure obtaining stored locations, initialize to 0
            lat_stored = 0;
            long_stored = 0;
        }

        // now do the main work
        try {
            // initialize a groupme communicator to post to groupme
            GroupmeCom gc = new GroupmeCom(botID);
            storedLoc.setLatitude(lat_stored);
            storedLoc.setLongitude(long_stored);

            // get all of the saved database locations
            MyDatabaseHandler db = new MyDatabaseHandler(context);
            db.open();
            List<MyLocation> locations = db.getAllLocations();
            db.close();

            // get the current location from best location
            MyLocation currLoc = GetLocationHandler.getCurrentLocation((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));

            // save this new location as the current location in persistent data for next run
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putFloat("long", (float) currLoc.getLongitude());
            editor.putFloat("lat", (float) currLoc.getLatitude());
            editor.commit();

            /*
            // TODO - REMOVE -- DEBUGGING INFO
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            gc.PostMessage(dateFormat.format(date) + " at " + currLoc.getLatitude() + ", " + currLoc.getLongitude());
            */

            // go through the list of saved locations, see if the user
            // is currently in any of those locations
            if (locations != null && !locations.isEmpty()) {
                int lastLocationIndex = -1;
                // initial loop to see if we are in any locations
                for (int i = 0; i < locations.size(); i++) {
                    MyLocation loc = locations.get(i);

                    // check to see if we are in this location
                    if (loc.IsPointInLocation(storedLoc)) {
                        lastLocationIndex = i;
                        break;
                    }
                }



                for (int i = 0; i < locations.size(); i++) {
                    MyLocation loc = locations.get(i);

                    // if you are IN location and your last location is not listed
                    // as IN that location, that means you entered that location
                    if (loc.IsPointInLocation(currLoc) && i != lastLocationIndex) {
                        gc.PostMessage(loc.EnterMessage());
                        Log.i("onHandleIntent", "EnterMessage");
                    } else if (!loc.IsPointInLocation(currLoc) && i == lastLocationIndex) {
                        // our last location was IN this location, but
                        // we are no longer in this location, that means we left
                        gc.PostMessage(loc.LeftMessage());
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