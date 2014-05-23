package com.arkaitzgarro.todolist_part_3;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements
		NewItemFragment.OnNewItemAddedListener {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private ArrayAdapter<String> aa;
	private ArrayList<String> todoItems;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.activity_main);

		// Get references to the Fragments
		FragmentManager fm = getFragmentManager();
		ToDoListFragment todoListFragment = (ToDoListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		// Create the array list of to do items
		todoItems = new ArrayList<String>();

		// Create the array adapter to bind the array to the listview
		int resID = R.layout.todolist_item;
		aa = new ArrayAdapter<String>(this, resID, todoItems);

		// Bind the array adapter to the listview.
		todoListFragment.setListAdapter(aa);
	}

	public void onNewItemAdded(String newItem) {
		todoItems.add(newItem);
		aa.notifyDataSetChanged();
	}

	@Override
	public void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);

		// A–adir a la lista
		todoItems.addAll(inState.getStringArrayList(ITEMS_ARRAY));

		// Notificar los cambios
		aa.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(ITEMS_ARRAY, todoItems);

		super.onSaveInstanceState(outState);
	}

}