package com.arkaitzgarro.todolist_part_2;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ToDoListFragment extends ListFragment {
	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private ArrayAdapter<String> aa;
	private ArrayList<String> todoItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Create the array list of to do items
		todoItems = new ArrayList<String>();

		// Create the array adapter to bind the array to the listview
		aa = new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1, todoItems);

		setListAdapter(aa);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addItem(String txt) {
		todoItems.add(0, txt);
		aa.notifyDataSetChanged();
	}

	@Override
	public void onActivityCreated(Bundle inState) {
		super.onActivityCreated(inState);

		if (inState != null) {
			// A–adir a la lista
			todoItems.addAll(inState.getStringArrayList(ITEMS_ARRAY));
			aa.notifyDataSetChanged();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(ITEMS_ARRAY, todoItems);

		super.onSaveInstanceState(outState);
	}
}
