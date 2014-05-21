package com.arkaitzgarro.earthquakes.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

class EarthquakeDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "EarthquakeProvider";

	public static final String DATABASE_NAME = "earthquakes.db";
	public static final String EARTHQUAKE_TABLE = "earthquakes";
	public static final int DATABASE_VERSION = 1;

	// Clase interna para declarar las constantes de columna
	public static final class Columns implements BaseColumns {
		// Column Names
		public static final String KEY_ID_STR = "id_str";
		public static final String KEY_PLACE = "place";
		public static final String KEY_TIME = "time";
		public static final String KEY_LOCATION_LAT = "latitude";
		public static final String KEY_LOCATION_LNG = "longitude";
		public static final String KEY_MAGNITUDE = "magnitude";
		public static final String KEY_URL = "url";
		public static final String KEY_CREATED_AT = "created_at";
		public static final String KEY_UPDATED_AT = "updated_at";
	}

	private static final String DATABASE_CREATE = "create table "
			+ EARTHQUAKE_TABLE + " (" + Columns._ID
			+ " integer primary key autoincrement, " + Columns.KEY_ID_STR
			+ " TEXT UNIQUE, " + Columns.KEY_TIME + " INTEGER, " + Columns.KEY_PLACE
			+ " TEXT, " + Columns.KEY_LOCATION_LAT + " FLOAT, " + Columns.KEY_LOCATION_LNG
			+ " FLOAT, " + Columns.KEY_MAGNITUDE + " FLOAT, " + Columns.KEY_URL + " TEXT,"
			+ Columns.KEY_CREATED_AT + " INTEGER, " + Columns.KEY_UPDATED_AT + " INTEGER"
			+ ");";

	public EarthquakeDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("EARTHQUAKE", DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + EARTHQUAKE_TABLE);
		onCreate(db);
	}

}
