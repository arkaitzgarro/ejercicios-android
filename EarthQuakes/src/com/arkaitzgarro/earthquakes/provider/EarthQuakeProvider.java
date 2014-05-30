package com.arkaitzgarro.earthquakes.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class EarthQuakeProvider extends ContentProvider {

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.arkaitzgarro.provider.earthquakes/earthquakes");

	private static final int EARTHQUAKES = 1;
	private static final int EARTHQUAKE_ID = 2;

	private static final int LIMIT = 50;

	private EarthquakeDatabaseHelper dbHelper;

	// Inner class for columns
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

	public static final String[] KEYS_ALL = { Columns._ID, Columns.KEY_ID_STR,
			Columns.KEY_PLACE, Columns.KEY_TIME, Columns.KEY_LOCATION_LAT,
			Columns.KEY_LOCATION_LNG, Columns.KEY_MAGNITUDE, Columns.KEY_URL };

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.arkaitzgarro.provider.earthquakes",
				"earthquakes", EARTHQUAKES);
		uriMatcher.addURI("com.arkaitzgarro.provider.earthquakes",
				"earthquakes/#", EARTHQUAKE_ID);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new EarthquakeDatabaseHelper(getContext(),
				EarthquakeDatabaseHelper.DATABASE_NAME, null,
				EarthquakeDatabaseHelper.DATABASE_VERSION);

		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case EARTHQUAKES:
			return "vnd.android.cursor.dir/vnd.arkaitzgarro.provider.earthquakes";
		case EARTHQUAKE_ID:
			return "vnd.android.cursor.item/vnd.arkaitzgarro.provider.earthquakes";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		SQLiteDatabase database;

		try {
			database = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			database = dbHelper.getReadableDatabase();
		}

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE);

		// If this is a row query, limit the result set to the passed in row.
		switch (uriMatcher.match(uri)) {
		case EARTHQUAKE_ID:
			qb.appendWhere(Columns._ID + "=" + uri.getPathSegments().get(1));
			break;
		default:
			break;
		}

		// If no sort order is specified, sort by date / time
		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = Columns.KEY_TIME + " DESC";
		} else {
			orderBy = sort;
		}

		// Apply the query to the underlying database.
		Cursor c = qb.query(database, projection, selection, selectionArgs,
				null, null, orderBy, String.valueOf(LIMIT));

		c.setNotificationUri(getContext().getContentResolver(), uri);

		// Return a cursor to the query result.
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase database;

		try {
			database = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			database = dbHelper.getReadableDatabase();
		}

		long now = System.currentTimeMillis();

		values.put(EarthquakeDatabaseHelper.Columns.KEY_CREATED_AT, now);
		values.put(EarthquakeDatabaseHelper.Columns.KEY_UPDATED_AT, now);

		long rowID = database.insert(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				null, values);

		if (rowID > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, rowID);

			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		}

		return null;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static class EarthquakeDatabaseHelper extends SQLiteOpenHelper {

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
				+ " TEXT UNIQUE, " + Columns.KEY_TIME + " INTEGER, "
				+ Columns.KEY_PLACE + " TEXT, " + Columns.KEY_LOCATION_LAT
				+ " FLOAT, " + Columns.KEY_LOCATION_LNG + " FLOAT, "
				+ Columns.KEY_MAGNITUDE + " FLOAT, " + Columns.KEY_URL
				+ " TEXT," + Columns.KEY_CREATED_AT + " INTEGER, "
				+ Columns.KEY_UPDATED_AT + " INTEGER" + ");";

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

}
