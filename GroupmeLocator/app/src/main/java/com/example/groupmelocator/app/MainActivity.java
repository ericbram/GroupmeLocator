package com.example.groupmelocator.app;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.location.Location;
import android.location.LocationManager;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get my current location
        getCurrentLocation();
    }

    public void getCurrentLocation() {

        // requestion location manager
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // create a new criteria (right now we have the most accurate we can)
        Criteria crit = new Criteria();
        crit.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);

        List<String> allprovs = lm.getAllProviders();
        Location lastknown = null;

        // get the location, we will base this on network first, then gps, then finally the best available

        if (allprovs.contains("network")) {
            // get the last known location using the best provider available
            lastknown = lm.getLastKnownLocation("network");
        } else if (allprovs.contains("gps")) {
            lastknown = lm.getLastKnownLocation("gps");
        } else {
            lastknown = lm.getLastKnownLocation(lm.getBestProvider(new Criteria(), true));
        }

        float[] results = new float[1];

        // compare to my work to see the distance (in meters)
        Location.distanceBetween(40.49027,-79.69206, lastknown.getLatitude(), lastknown.getLongitude(), results);

        // get the distance in meters
        double meters = results[0];

        // convert to miles
        double miles = meters * 0.000621371;

        // set the text if the textviewer
        setContentView(R.layout.fragment_main);
        TextView textViewToChange = (TextView) findViewById(R.id.distanceMeasurement);
        textViewToChange.setText("You are " + ((double)Math.round(miles * 100) / 100) + " miles from work.");



        // do this for home now


        // compare to my work to see the distance (in meters)
        Location.distanceBetween(40.449267,-79.849856, lastknown.getLatitude(), lastknown.getLongitude(), results);

        // get the distance in meters
        meters = results[0];

        // convert to miles
        miles = meters * 0.000621371;

        // set the text if the textviewer
        textViewToChange = (TextView) findViewById(R.id.distance2Measurement);
        textViewToChange.setText("You are " + ((double)Math.round(miles * 100) / 100) + " miles from home.");
    }

    public void refreshLocation(View view) {
      getCurrentLocation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_addLocation) {
            Intent intent = new Intent(this, AddLocationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}


/*

public class TestDatabaseActivity extends ListActivity {
  private CommentsDataSource datasource;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    datasource = new CommentsDataSource(this);
    datasource.open();

    List<Comment> values = datasource.getAllComments();

    // use the SimpleCursorAdapter to show the
    // elements in a ListView
    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  // Will be called via the onClick attribute
  // of the buttons in main.xml
  public void onClick(View view) {
    @SuppressWarnings("unchecked")
    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
    Comment comment = null;
    switch (view.getId()) {
    case R.id.add:
      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
      int nextInt = new Random().nextInt(3);
      // save the new comment to the database
      comment = datasource.createComment(comments[nextInt]);
      adapter.add(comment);
      break;
    case R.id.delete:
      if (getListAdapter().getCount() > 0) {
        comment = (Comment) getListAdapter().getItem(0);
        datasource.deleteComment(comment);
        adapter.remove(comment);
      }
      break;
    }
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }

}
 */
