package com.arkaitzgarro.configchange;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class DialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dialog);
		
		Log.i("CHANGE", "Dialog.onCreate");
	}

	@Override
    protected void onStart() {
        super.onStart();
        Log.i("CHANGE", "Dialog.onStart");
    }
	
	@Override
	protected void onResume() {
		super.onResume();
        Log.i("CHANGE", "Dialog.onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
        Log.i("CHANGE", "Dialog.onPause");
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("CHANGE", "Dialog.onRestart");
    }
    
    @Override
	protected void onStop() {
		super.onStop();
        Log.i("CHANGE", "Dialog.onStop");
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
        Log.i("CHANGE", "Dialog.onDestroy");
	}
	
	public void finishDialog(View v) {
		Log.i("CHANGE", "Close Dialog");
		this.finish();
	}

}
