package com.arkaitzgarro.earthquake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EarthquakeDB {
	
	// Column Names
	public static final String KEY_ID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_LOCATION_LAT = "latitude";
	public static final String KEY_LOCATION_LNG = "longitude";
	public static final String KEY_MAGNITUDE = "magnitude";
	public static final String KEY_LINK = "link";
	
	public static final String[] KEYS_ALL = { KEY_ID, KEY_DATE, KEY_DETAILS,
											   KEY_SUMMARY, KEY_LOCATION_LAT, KEY_LOCATION_LNG,
											   KEY_MAGNITUDE, KEY_LINK};
	
	private Context mContext;
	private SQLiteDatabase mDatabase;
	private EarthquakeDatabaseHelper dbHelper;
	
	public EarthquakeDB(Context context) {
		mContext = context;
	}
	
	public void open() throws SQLException {
		dbHelper = new EarthquakeDatabaseHelper(mContext,
			    EarthquakeDatabaseHelper.DATABASE_NAME, null,
			    EarthquakeDatabaseHelper.DATABASE_VERSION);
		
		mDatabase = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
		dbHelper = null;
		mDatabase = null;
	}
	
	public long createRow(ContentValues values) {
		return mDatabase.insert(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, null, values);
	}
	
	public boolean updateRow(long rowId, ContentValues values) {
		return mDatabase.update(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, values, KEY_ID + "=" + rowId, null) > 0;
	}
	
	public boolean deleteRow(long rowId) {
		return mDatabase.delete(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}
	
	public Cursor queryAll() {
		return mDatabase.query(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				KEYS_ALL,
				null, null, null, null,
				KEY_DATE + " DESC");
	}
	
	public Cursor query(String[] columns, String where, String[] whereArgs, String groupBy, String having, String orderBy) {
		return mDatabase.query(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				columns,
				where,
				whereArgs,
				groupBy,
				having,
				orderBy);
	}

	private static class EarthquakeDatabaseHelper extends SQLiteOpenHelper {

		private static final String TAG = "EarthquakeProvider";

		public static final String DATABASE_NAME = "earthquakes.db";
		public static final String EARTHQUAKE_TABLE = "earthquakes";
		public static final int DATABASE_VERSION = 2;

		private static final String DATABASE_CREATE = "create table "
				+ EARTHQUAKE_TABLE + " ("
				+ KEY_ID + " integer primary key autoincrement, "
				+ KEY_DATE + " INTEGER, "
				+ KEY_DETAILS + " TEXT, "
				+ KEY_SUMMARY + " TEXT, "
				+ KEY_LOCATION_LAT+ " FLOAT, "
				+ KEY_LOCATION_LNG + " FLOAT, "
				+ KEY_MAGNITUDE + " FLOAT, "
				+ KEY_LINK + " TEXT);";

		public EarthquakeDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		@Override
	    public void onCreate(SQLiteDatabase db) {
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
