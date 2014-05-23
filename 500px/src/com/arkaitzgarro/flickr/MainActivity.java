package com.arkaitzgarro.flickr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Handler gridHandler;
	private ImageAdapter ia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//		 StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		images = new ArrayList<String>();
//		aa = new ArrayAdapter<String>(this,
//									  android.R.layout.simple_gallery_item,
//									  images);
		ia = new ImageAdapter(this, getString(R.string.url_500px));
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
//	    gridview.setAdapter(aa);
		gridview.setAdapter(ia);
		
		gridHandler = new Handler() {
            public void handleMessage(Message msg) {
                ia.notifyDataSetChanged();
            }
       };

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}
	
	public void notifyDataSetChanged() {
		ia.notifyDataSetChanged();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
