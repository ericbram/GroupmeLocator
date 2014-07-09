package com.example.groupmelocator.app;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Eric on 6/8/2014.
 */
public class GroupmeCom {

    String bot_ID = "";

    public GroupmeCom(String ID) {
        bot_ID = ID;
    }

    public void PostMessage(String message) {
        try {
            if (bot_ID == null || bot_ID == "") {
                // bot ID not set
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("bot_id", bot_ID);
            obj.put("text", message);
            Post(obj.toString(), "https://api.groupme.com/v3/bots/post");
        }
        catch(Exception e) {

        }
    }


    // this posts to Groupme using an Async task (required)
    public void Post(String message, String url) throws MalformedURLException {
        new PostTask().execute(message, url);
    }



    private class PostTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            HttpsURLConnection urlConnection = null;
            try {

                // create the URL object and connection from the URL
                URL url = new URL(urls[1]);
                urlConnection = (HttpsURLConnection) url.openConnection();

                // Set up the POST
                urlConnection.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                String message = urls[0];

                // SEND POST
                out.write(message);
                out.close();

                // read back response
                InputStreamReader in = new InputStreamReader((InputStream) urlConnection.getContent());
                BufferedReader buff = new BufferedReader(in);
                String line;

                // not doing anything with response at this time
                String text = "";
                do {
                    line = buff.readLine();
                    text = text + line +  "  ";
                } while (line != null);
            }
            catch (Exception e) {
                Log.e("PostTask", e.getMessage());
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }
}


