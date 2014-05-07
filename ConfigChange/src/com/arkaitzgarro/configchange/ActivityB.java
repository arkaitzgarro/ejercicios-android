package com.arkaitzgarro.configchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		
		Log.i("CHANGE", "ActivityB.onCreate");
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        Log.i("CHANGE", "ActivityB.onStart");
    }
	
	@Override
	protected void onResume() {
		super.onResume();
        Log.i("CHANGE", "ActivityB.onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
        Log.i("CHANGE", "ActivityB.onPause");
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("CHANGE", "ActivityB.onRestart");
    }
    
    @Override
	protected void onStop() {
		super.onStop();
        Log.i("CHANGE", "ActivityB.onStop");
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
        Log.i("CHANGE", "ActivityB.onDestroy");
	}
    
    public void startA(View v) {
    	Log.i("CHANGE", "Start activity A");
    	
    	Intent intent = new Intent(this, ActivityA.class);
        startActivity(intent);
    }
    
    public void startC(View v) {
    	Log.i("CHANGE", "Start activity C");
    	Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }
    
    public void close(View v) {
    	Log.i("CHANGE", "Close activity B");
    	finish();
    }

}
