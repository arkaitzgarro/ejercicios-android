package com.arkaitzgarro.todolist_part_4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends Activity implements
		NewItemFragment.OnNewItemAddedListener {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private ToDoItemAdapter aa;
	private ArrayList<ToDoItem> todoItems;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.activity_main);

		// Get references to the Fragments
		FragmentManager fm = getFragmentManager();
		ToDoListFragment todoListFragment = (ToDoListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		// Create the array list of to do items
		todoItems = new ArrayList<ToDoItem>();

		// Create the array adapter to bind the array to the listview
		int resID = R.layout.todolist_item;
		aa = new ToDoItemAdapter(this, resID, todoItems);

		// Bind the array adapter to the listview.
		todoListFragment.setListAdapter(aa);
	}

	public void onNewItemAdded(String title) {
		ToDoItem item = new ToDoItem(title);
		todoItems.add(0, item);
		aa.notifyDataSetChanged();
	}

	@Override
	public void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);

		ArrayList<ToDoItem> tmp = inState.getParcelableArrayList(ITEMS_ARRAY);

		// A–adir a la lista
		todoItems.addAll(tmp);

		// Notificar los cambios
		aa.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(ITEMS_ARRAY, todoItems);

		super.onSaveInstanceState(outState);
	}

}