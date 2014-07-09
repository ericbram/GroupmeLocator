package com.example.groupmelocator.app;

import android.content.Context;
import android.content.SharedPreferences;
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
public class AddBotIDActivity extends ActionBarActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_botid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_botid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void btnSetBotIDPushed(View view) {
        // get the ID

        EditText botid = (EditText)findViewById(R.id.newBotID);

        // get the string values
        String botidStr = botid.getText().toString();

        if (botidStr == null || botidStr == "") {
            // dont set if it's not a real ID
            return;
        }

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("botid", botidStr);
        editor.commit();
        finish();
    }
}