package com.arkaitzgarro.todolist_part_2;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements
		NewItemFragment.OnNewItemAddedListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.activity_main);

		if(savedInstanceState == null) {
			// Get references to the Fragments
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.editTextLayout, new NewItemFragment());
			fragmentTransaction.add(R.id.listLayout, new ToDoListFragment(), "list");
			fragmentTransaction.commit();
		}
	}

	public void addItem(String newItem) {
		((ToDoListFragment)getFragmentManager().findFragmentByTag("list")).addItem(newItem);
	}
}