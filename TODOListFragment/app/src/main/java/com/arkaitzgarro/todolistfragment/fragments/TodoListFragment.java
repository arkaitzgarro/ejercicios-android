package com.arkaitzgarro.todolistfragment.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arkaitzgarro.todolistfragment.DetailActivity;
import com.arkaitzgarro.todolistfragment.R;
import com.arkaitzgarro.todolistfragment.adapters.ToDoAdapter;
import com.arkaitzgarro.todolistfragment.model.ToDo;

import java.util.ArrayList;

/**
 * A fragment representing a list of TODOs.
 */
public class TodoListFragment extends ListFragment implements InputFragment.TODOItemListener {

    private final String TODOS_KEY = "TODO_KEY";
    public static final String TODO_ITEM = "TODO_ITEM";

    private ArrayList<ToDo> todos;
    private ArrayAdapter<ToDo> aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        todos = new ArrayList<>();
        aa = new ToDoAdapter(getActivity(), R.layout.todo_list_item, todos);

        if (savedInstanceState != null) {
            ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);

            todos.addAll(tmp);
        }

        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ToDo todo = todos.get(position);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(TODO_ITEM, todo);

        startActivity(detailIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TODOS_KEY, todos);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void addTodo(ToDo todo) {
        todos.add(0, todo);
        aa.notifyDataSetChanged();
    }
}
