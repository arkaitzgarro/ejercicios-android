package com.arkaitzgarro.earthquakes.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.activities.MainActivity;
import com.arkaitzgarro.earthquakes.database.EarthQuakeDB;
import com.arkaitzgarro.earthquakes.model.Coordinate;
import com.arkaitzgarro.earthquakes.model.EarthQuake;
import com.arkaitzgarro.earthquakes.tasks.DownloadEarthquakesTask;

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

public class DownloadEarthquakesService extends Service {

    private final String EARTHQUAKE = "EARTHQUAKE";

    private EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate() {
        super.onCreate();

        earthQuakeDB = new EarthQuakeDB(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d(EARTHQUAKE, "Start download service command");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuakes(getString(R.string.earthquakes_url));
            }
        });
        t.start();

        return Service.START_STICKY;
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
                    count++;
                }
            }

            sendNotification(count);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            stopSelf();
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

    private void sendNotification(int count) {

        Intent intentToFire = new Intent(this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this, 0, intentToFire, 0);

        Notification.Builder builder = new Notification.Builder(DownloadEarthquakesService.this);

        builder
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.count_earthquakes, count))
            .setWhen(System.currentTimeMillis())
            .setDefaults(Notification.DEFAULT_SOUND)
            .setSound(
                    RingtoneManager.getDefaultUri(
                            RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(activityIntent)
            .setAutoCancel(true)
            ;

        Notification notification = builder.build();

        NotificationManager notificationManager
                = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        int NOTIFICATION_REF = 1;
        notificationManager.notify(NOTIFICATION_REF, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
