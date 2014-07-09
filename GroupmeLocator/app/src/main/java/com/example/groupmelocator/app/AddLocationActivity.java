package com.example.groupmelocator.app;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.List;

// main class on startup
public class AddLocationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getActionBar().setTitle("Store Current Location");
    }

    public void btnAddLocationPushed(View view) {
        MyDatabaseHandler db = null;
        try
        {
            // open the database
            db = new MyDatabaseHandler(this);
            db.open();

            // get current location
            MyLocation myloc = GetLocationHandler.getCurrentLocation((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));

            // get values from main screen
            EditText locName   = (EditText)findViewById(R.id.newLocationName);
            EditText locRadius   = (EditText)findViewById(R.id.newLocRadius);

            // get the string values
            String locNamestr = locName.getText().toString();
            String locRadiusstr = locRadius.getText().toString();

            if (locNamestr != "" && locRadiusstr != "") {
                db.createMyLocation(locNamestr, myloc.getLatitude(), myloc.getLongitude(), Double.parseDouble(locRadiusstr));
                finish();
            }
        }
        catch (Exception e) {
            Log.e("AddLocationActivity", e.getMessage());
        }
        finally {
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}

