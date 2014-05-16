package com.arkaitzgarro.downloads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DownloadManager downloadManager;
	private long myReference;
	private Handler gridHandler;
	private Image[] images;

	private TextView lblName, lblSize, lblStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lblName = (TextView) findViewById(R.id.fileName);
		lblSize = (TextView) findViewById(R.id.fileSize);
		lblStatus = (TextView) findViewById(R.id.fileStatus);

		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				Uri uri = Uri
						.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip");
				Request request = new Request(uri);
				request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setDestinationInExternalFilesDir(MainActivity.this,
						Environment.DIRECTORY_DOWNLOADS, "icon_templates.zip");
				myReference = downloadManager.enqueue(request);
			}
		});

		Button startJSON = (Button) findViewById(R.id.startJSON);
		startJSON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						downloadJSON();
					}
				});
				t.start();
			}
		});

		BroadcastReceiver downloadComplete = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("DOWNLOADS", "downloadComplete()");
				long reference = intent.getLongExtra(
						DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				getDownloadInfo(reference);
			}
		};
		registerReceiver(downloadComplete, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));

		BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent data) {
				Log.d("DOWNLOADS", "onNotificationClick()");
				long reference = data.getLongExtra(
						DownloadManager.EXTRA_DOWNLOAD_ID, -1);

				if (reference == myReference) {
					Intent intent = new Intent(context, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
					context.startActivity(intent);
				}
			}
		};
		registerReceiver(onNotificationClick, new IntentFilter(
				DownloadManager.ACTION_NOTIFICATION_CLICKED));
	}

	private void downloadJSON() {
		JSONObject json;

		String uri = getString(R.string.uri);

		try {
			URL url = new URL(uri);

			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try {
					BufferedReader streamReader = new BufferedReader(
							new InputStreamReader(
									httpConnection.getInputStream(), "UTF-8"));
					StringBuilder responseStrBuilder = new StringBuilder();

					String inputStr;
					while ((inputStr = streamReader.readLine()) != null)
						responseStrBuilder.append(inputStr);

					json = new JSONObject(responseStrBuilder.toString());
					processJSON(json);
				} catch (JSONException e) {
					Log.e("500PX",
							"Error al leer el fichero JSON: " + e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			Log.d("500PX", "Malformed URL Exception.", e);
		} catch (IOException e) {
			Log.d("500PX", "IO Exception.", e);
		}
	}

	private void processJSON(JSONObject json) {
		try {
			JSONArray imagesFields = json.getJSONArray("photos");
			images = new Image[imagesFields.length()];

			for (int i = 0; i < imagesFields.length(); i++) {
				JSONObject image = imagesFields.getJSONObject(i);

				images[i] = new Image(image.getInt("id"),
						image.getString("name"), image.getString("image_url"));

				Log.d("DOWNLOADS", images[i].toString());
			}
		} catch (JSONException e) {
			Log.e("DOWNLOADS", e.getMessage());
		}

	}

	private void getDownloadInfo(long reference) {
		Query downloadQuery = new Query();
		downloadQuery.setFilterById(reference);

		Cursor downloads = downloadManager.query(downloadQuery);

		int filenameIdx = downloads
				.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
		int statusIdx = downloads.getColumnIndex(DownloadManager.COLUMN_STATUS);
		int sizeIdx = downloads
				.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);

		while (downloads.moveToNext()) {
			String filename = downloads.getString(filenameIdx);
			lblName.setText(filename);

			long size = downloads.getLong(sizeIdx);
			lblSize.setText(String.valueOf(size));

			int status = downloads.getInt(statusIdx);
			switch (status) {
			case DownloadManager.STATUS_SUCCESSFUL:
				lblStatus.setText(android.R.string.ok);
				break;
			default:
				break;
			}
		}

		downloads.close();
	}

}
