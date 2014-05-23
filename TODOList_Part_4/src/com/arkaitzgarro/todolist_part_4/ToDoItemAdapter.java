package com.arkaitzgarro.todolist_part_4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

	int resource;

	public ToDoItemAdapter(Context context, int resource, List<ToDoItem> items) {
		super(context, resource, items);
		this.resource = resource;
	}

	/**
	 * Aquí tenemos que devolver el View a representar. En este método nos pasan
	 * 3 valores. El primero es la posición del elemento, el segundo es el View
	 * a utilizar que será uno que ya no esté visible y que lo reutilizaremos, y
	 * el último es el ViewGroup, es en nuestro caso, el ListView.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout todoView;

		// Obtener el item a mostrar
		ToDoItem item = getItem(position);

		// Obtener los datos del item
		String taskString = item.getTask();
		Date createdDate = item.getCreated();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(createdDate);

		if (convertView == null) {
			// Si no existe la vista la creamos
			todoView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(resource, todoView, true);
		} else {
			todoView = (LinearLayout) convertView;
		}

		TextView dateView = (TextView) todoView.findViewById(R.id.rowDate);
		TextView taskView = (TextView) todoView.findViewById(R.id.row);

		dateView.setText(dateString);
		taskView.setText(taskString);

		return todoView;
	}
}
