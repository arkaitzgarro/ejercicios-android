package com.arkaitzgarro.configchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		
		Log.i("CHANGE", "ActivityA.onCreate");
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        Log.i("CHANGE", "ActivityA.onStart");
    }
	
	@Override
	protected void onResume() {
		super.onResume();
        Log.i("CHANGE", "ActivityA.onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
        Log.i("CHANGE", "ActivityA.onPause");
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("CHANGE", "ActivityA.onRestart");
    }
    
    @Override
	protected void onStop() {
		super.onStop();
        Log.i("CHANGE", "ActivityA.onStop");
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
        Log.i("CHANGE", "ActivityA.onDestroy");
	}
    
    public void startB(View v) {
    	Log.i("CHANGE", "Start activity B");
    	
    	Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
    
    public void startC(View v) {
    	Log.i("CHANGE", "Start activity C");
    	
    	Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }
    
    public void startDialog(View v) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }
    
    public void close(View v) {
    	Log.i("CHANGE", "Close activity A");
    	finish();
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
