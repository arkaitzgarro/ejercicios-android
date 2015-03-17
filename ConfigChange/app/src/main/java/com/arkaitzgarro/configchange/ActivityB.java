package com.arkaitzgarro.configchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityB extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Log.d("CHANGE", "ActivityB onCreate()");

        Button btnOpen = (Button) findViewById(R.id.btnOpen);
        Button btnClose = (Button) findViewById(R.id.btnClose);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityB openA");

                Intent openB = new Intent(ActivityB.this, ActivityA.class);
                startActivity(openB);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityB close");

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("CHANGE", "ActivityB onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("CHANGE", "ActivityB onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("CHANGE", "ActivityB onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("CHANGE", "ActivityB onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("CHANGE", "ActivityB onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("CHANGE", "ActivityB onDestroy()");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_b, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
