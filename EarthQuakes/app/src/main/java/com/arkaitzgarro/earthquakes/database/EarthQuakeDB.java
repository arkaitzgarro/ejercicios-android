package com.arkaitzgarro.earthquakes.database;

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

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    // Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_PLACE = "place";
    public static final String KEY_LOCATION_LAT = "latitude";
    public static final String KEY_LOCATION_LNG = "longitude";
    public static final String KEY_MAGNITUDE = "magnitude";
    public static final String KEY_DEPTH = "depth";
    public static final String KEY_LINK = "link";

    public static class Columns implements BaseColumns {

    }

    public static final String[] ALL_COLUMNS = {
            KEY_ID,
            KEY_DATE,
            KEY_PLACE,
            KEY_LOCATION_LAT,
            KEY_LOCATION_LNG,
            KEY_MAGNITUDE,
            KEY_DEPTH,
            KEY_LINK
    };

    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    public void insertEarthQuake(EarthQuake earthQuake) {
        ContentValues insert = new ContentValues();

        insert.put(KEY_ID, earthQuake.getId());
        insert.put(KEY_DATE, earthQuake.getTime().getTime());
        insert.put(KEY_PLACE, earthQuake.getPlace());
        insert.put(KEY_LOCATION_LAT, earthQuake.getCoords().getLat());
        insert.put(KEY_LOCATION_LNG, earthQuake.getCoords().getLng());
        insert.put(KEY_MAGNITUDE, earthQuake.getMagnitude());
        insert.put(KEY_DEPTH, earthQuake.getCoords().getDepth());
        insert.put(KEY_LINK, earthQuake.getUrl());

        try {
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, insert);
        } catch (SQLException ex) {
            Log.e(EARTHQUAKES, ex.getMessage());
        }
    }

    public List<EarthQuake> getAll() {
        return query(null, null);
    }

    public List<EarthQuake> getAllByMagnitude(int magnitude) {
        String where = KEY_MAGNITUDE + " >= ?";
        String[] whereArgs = {
                String.valueOf(magnitude)
        };

        return query(where, whereArgs);
    }

    public EarthQuake getEarthQuake(String id) {
        String where = KEY_ID + " = ?";
        String[] whereArgs = {
                id
        };

        List<EarthQuake> all = query(where, whereArgs);

        return (all.size() > 0) ? all.get(0) : null;
    }

    private List<EarthQuake> query(String where, String[] whereArgs) {

        List<EarthQuake> earthQuakes = new ArrayList<>();

        Cursor cursor = db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                ALL_COLUMNS,
                where,
                whereArgs,
                null,
                null,
                KEY_DATE + " DESC"
        );

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i  = 0; i < ALL_COLUMNS.length; i++) {
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        while (cursor.moveToNext()) {
            EarthQuake earthQuake = new EarthQuake();

            earthQuake.setId(cursor.getString(indexes.get(KEY_ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
            earthQuake.setLatitude(cursor.getDouble(indexes.get(KEY_LOCATION_LAT)));
            earthQuake.setLongitude(cursor.getDouble(indexes.get(KEY_LOCATION_LNG)));
            earthQuake.setDepth(cursor.getDouble(indexes.get(KEY_DEPTH)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
            earthQuake.setTime(cursor.getLong(indexes.get(KEY_DATE)));
            earthQuake.setUrl(cursor.getString(indexes.get(KEY_LINK)));

            earthQuakes.add(earthQuake);
        }

        return earthQuakes;
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;

        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" +
                KEY_ID + " TEXT PRIMARY KEY," +
                KEY_PLACE + " TEXT," +
                KEY_MAGNITUDE + " REAL," +
                KEY_LOCATION_LAT + " REAL," +
                KEY_LOCATION_LNG + " REAL," +
                KEY_DEPTH + " REAL," +
                KEY_LINK + " TEXT," +
                KEY_DATE + " INTEGER)";

        private EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
