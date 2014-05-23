package com.arkaitzgarro.todolist_part_4;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoItem implements Parcelable {
	String task;
	Date created;

	public String getTask() {
		return task;
	}

	public Date getCreated() {
		return created;
	}

	public ToDoItem(String _task) {
		this(_task, new Date(java.lang.System.currentTimeMillis()));
	}

	public ToDoItem(String _task, Date _created) {
		task = _task;
		created = _created;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(created);

		return "(" + dateString + ") " + task;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}
}
