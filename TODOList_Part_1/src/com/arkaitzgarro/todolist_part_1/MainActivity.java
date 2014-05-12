package com.arkaitzgarro.todolist_part_1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private EditText myEditText;
	private ListView myListView;

	// Create the Array List of to do items
	private ArrayList<String> items;
	// Create the Array Adapter to bind the array to the List View
	private ArrayAdapter<String> aa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your View
		setContentView(R.layout.activity_main);

		items = new ArrayList<String>();

		// Get references to UI widgets
		myListView = (ListView) findViewById(R.id.myListView);
		myEditText = (EditText) findViewById(R.id.myEditText);

		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);

		// Bind the Array Adapter to the List View
		myListView.setAdapter(aa);
		
		myEditText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
							|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
						MainActivity.this.addItem(myEditText.getText().toString());
						return true;
					}
				return false;
			}
		});

	}
	
	public void addItem(String item) {
		items.add(0, item);
		aa.notifyDataSetChanged();
		myEditText.setText("");
	}
	
	public void addItem(View v) {
		this.addItem(myEditText.getText().toString());
	}

	@Override
	public void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);

		// A–adir a la lista
		items.addAll(inState.getStringArrayList(ITEMS_ARRAY));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(ITEMS_ARRAY, items);

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Notificar los cambios
		aa.notifyDataSetChanged();
	}
}
