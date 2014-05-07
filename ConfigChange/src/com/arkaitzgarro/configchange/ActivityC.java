package com.arkaitzgarro.configchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
		
		Log.i("CHANGE", "ActivityC.onCreate");
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        Log.i("CHANGE", "ActivityC.onStart");
    }
	
	@Override
	protected void onResume() {
		super.onResume();
        Log.i("CHANGE", "ActivityC.onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
        Log.i("CHANGE", "ActivityC.onPause");
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("CHANGE", "ActivityC.onRestart");
    }
    
    @Override
	protected void onStop() {
		super.onStop();
        Log.i("CHANGE", "ActivityC.onStop");
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
        Log.i("CHANGE", "ActivityC.onDestroy");
	}
    
    public void startA(View v) {
    	Log.i("CHANGE", "Start activity A");
    	
    	Intent intent = new Intent(this, ActivityA.class);
        startActivity(intent);
    }
    
    public void startB(View v) {
    	Log.i("CHANGE", "Start activity B");
    	
    	Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
    
    public void close(View v) {
    	Log.i("CHANGE", "Close activity C");
    	finish();
    }

}
