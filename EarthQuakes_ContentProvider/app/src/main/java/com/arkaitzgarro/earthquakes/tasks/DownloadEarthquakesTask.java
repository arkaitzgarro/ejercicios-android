package com.arkaitzgarro.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.arkaitzgarro.earthquakes.providers.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.model.Coordinate;
import com.arkaitzgarro.earthquakes.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by arkaitz on 25/03/15.
 */
public class DownloadEarthquakesTask extends AsyncTask<String, EarthQuake, Integer> {

    private EarthQuakeDB earthQuakeDB;

    public interface AddEarthQuakeInterface {
        public void notifyTotal(int total);
    }

    private final String EARTHQUAKE = "EARTHQUAKE";

    private AddEarthQuakeInterface target;

    public DownloadEarthquakesTask(Context context, AddEarthQuakeInterface target) {
        this.target = target;

        earthQuakeDB = new EarthQuakeDB(context);
    }

    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;

        if (urls.length > 0) {
            count = updateEarthQuakes(urls[0]);
        }

        return new Integer(count);
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);

        target.notifyTotal(count.intValue());
    }

    private int updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;
        int count = 0;

        try {
            URL url = new URL(earthquakesFeed);

            // Create a new HTTP URL connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return count;
    }

    private void processEarthQuakeTask(JSONObject jsonObj) {
        try {
            // Obtain coordinates
            JSONArray jsonCoords = jsonObj.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));

            JSONObject properties = jsonObj.getJSONObject("properties");

            EarthQuake earthQuake = new EarthQuake();
            earthQuake.setId(jsonObj.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setTime(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d(EARTHQUAKE, earthQuake.toString());

            earthQuakeDB.insertEarthQuake(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
