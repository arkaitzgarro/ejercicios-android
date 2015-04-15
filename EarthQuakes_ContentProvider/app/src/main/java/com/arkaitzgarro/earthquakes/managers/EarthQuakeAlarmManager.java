package com.arkaitzgarro.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.arkaitzgarro.earthquakes.services.DownloadEarthquakesService;

/**
 * Created by arkaitz on 01/04/15.
 */
public class EarthQuakeAlarmManager {

    public static void setAlarm(Context context, long interval) {
        // Get a reference to the Alarm Manager
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to wake the device if sleeping.
        int alarmType = AlarmManager.RTC;

        // Create a Pending Intent that will start a service
        Intent intentToFire = new Intent(context, DownloadEarthquakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToFire, 0);

        // Set the alarm
        alarmManager.setInexactRepeating(alarmType, interval, interval, alarmIntent);
    }

    public static void cancelAlarm(Context context) {
        // Get a reference to the Alarm Manager
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Create a Pending Intent that will start a service
        Intent intentToFire = new Intent(context, DownloadEarthquakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToFire, 0);

        alarmManager.cancel(alarmIntent);
    }

    public static void updateAlarm(Context context, long interval) {
        setAlarm(context, interval);
    }
}
