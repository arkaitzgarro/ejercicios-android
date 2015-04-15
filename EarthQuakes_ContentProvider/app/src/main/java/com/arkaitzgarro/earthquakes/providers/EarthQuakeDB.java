package com.arkaitzgarro.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.arkaitzgarro.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arkaitz on 27/03/15.
 */
public class EarthQuakeDB {

    private final String EARTHQUAKES = "EARTHQUAKES";

    private Context context;

    public static final String[] ALL_COLUMNS = {
            EarthQuakesProvider.Columns._ID,
            EarthQuakesProvider.Columns.KEY_DATE,
            EarthQuakesProvider.Columns.KEY_PLACE,
            EarthQuakesProvider.Columns.KEY_LOCATION_LAT,
            EarthQuakesProvider.Columns.KEY_LOCATION_LNG,
            EarthQuakesProvider.Columns.KEY_MAGNITUDE,
            EarthQuakesProvider.Columns.KEY_DEPTH,
            EarthQuakesProvider.Columns.KEY_LINK
    };

    public EarthQuakeDB(Context context) {
        this.context = context;
    }

    public void insertEarthQuake(EarthQuake earthQuake) {
        ContentValues insert = new ContentValues();

        insert.put(EarthQuakesProvider.Columns._ID, earthQuake.getId());
        insert.put(EarthQuakesProvider.Columns.KEY_DATE, earthQuake.getTime().getTime());
        insert.put(EarthQuakesProvider.Columns.KEY_PLACE, earthQuake.getPlace());
        insert.put(EarthQuakesProvider.Columns.KEY_LOCATION_LAT, earthQuake.getCoords().getLat());
        insert.put(EarthQuakesProvider.Columns.KEY_LOCATION_LNG, earthQuake.getCoords().getLng());
        insert.put(EarthQuakesProvider.Columns.KEY_MAGNITUDE, earthQuake.getMagnitude());
        insert.put(EarthQuakesProvider.Columns.KEY_DEPTH, earthQuake.getCoords().getDepth());
        insert.put(EarthQuakesProvider.Columns.KEY_LINK, earthQuake.getUrl());

        ContentResolver cr = context.getContentResolver();
        cr.insert(EarthQuakesProvider.CONTENT_URI, insert);
    }

    public List<EarthQuake> getAll() {
        //return query(null, null);

        return null;
    }

    public List<EarthQuake> getAllByMagnitude(int magnitude) {

        String where = EarthQuakesProvider.Columns.KEY_MAGNITUDE + " >= ?";
        String[] whereArgs = {
                String.valueOf(magnitude)
        };

        return query(where, whereArgs);
    }

    public EarthQuake getEarthQuake(String id) {
//        String where = EarthQuakesProvider.Columns._ID + " = ?";
//        String[] whereArgs = {
//                id
//        };
//
//        List<EarthQuake> all = query(where, whereArgs);

        // return (all.size() > 0) ? all.get(0) : null;
        return null;
    }

    private List<EarthQuake> query(String where, String[] whereArgs) {

        ContentResolver cr = context.getContentResolver();

        List<EarthQuake> earthQuakes = new ArrayList<>();

        String orderBy = EarthQuakesProvider.Columns.KEY_DATE + " DESC";

        Cursor cursor = cr.query(
                EarthQuakesProvider.CONTENT_URI,
                ALL_COLUMNS,
                where,
                whereArgs,
                orderBy
        );

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i  = 0; i < ALL_COLUMNS.length; i++) {
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        while (cursor.moveToNext()) {
            EarthQuake earthQuake = new EarthQuake();

            earthQuake.setId(cursor.getString(indexes.get(EarthQuakesProvider.Columns._ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(EarthQuakesProvider.Columns.KEY_PLACE)));
            earthQuake.setLatitude(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_LOCATION_LAT)));
            earthQuake.setLongitude(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_LOCATION_LNG)));
            earthQuake.setDepth(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_DEPTH)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_MAGNITUDE)));
            earthQuake.setTime(cursor.getLong(indexes.get(EarthQuakesProvider.Columns.KEY_DATE)));
            earthQuake.setUrl(cursor.getString(indexes.get(EarthQuakesProvider.Columns.KEY_LINK)));

            earthQuakes.add(earthQuake);
        }

        cursor.close();

        return earthQuakes;
    }
}
