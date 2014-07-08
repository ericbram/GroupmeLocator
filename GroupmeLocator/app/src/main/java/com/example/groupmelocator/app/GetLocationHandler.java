package com.example.groupmelocator.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 6/8/2014.
 */
public class GetLocationHandler {

    public static MyLocation getCurrentLocation(LocationManager lm) {
        MyLocation myloc = null;
        try {
            myloc = new MyLocation();
            // create a new criteria (right now we have the most accurate we can)
            Criteria crit = new Criteria();
            crit.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);

            List<String> allprovs = lm.getAllProviders();
            Location lastknown = null;

            // get the location, we will base this on network first, then gps, then finally the best available

            if (allprovs == null || allprovs.isEmpty())
                return myloc;

            if (allprovs.contains("network")) {
                // get the last known location using the best provider available
                lastknown = lm.getLastKnownLocation("network");
            } else if (allprovs.contains("gps")) {
                lastknown = lm.getLastKnownLocation("gps");
            } else {
                lastknown = lm.getLastKnownLocation(lm.getBestProvider(new Criteria(), true));
            }

            myloc.setLatitude(lastknown.getLatitude());
            myloc.setLongitude(lastknown.getLongitude());
        }
        catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return myloc;
    }
}
