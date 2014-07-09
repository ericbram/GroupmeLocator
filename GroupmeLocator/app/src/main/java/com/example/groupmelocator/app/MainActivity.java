package com.example.groupmelocator.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        Intent intentAlarm = new Intent(this, Alarm.class);
        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        // 5 minutes
        int time = 5 * 60 * 1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 10000, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
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
        if (id == R.id.action_addBotID) {
            Intent intent = new Intent(this, AddBotIDActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}