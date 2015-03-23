package com.arkaitzgarro.todolistfragment;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.arkaitzgarro.todolistfragment.fragments.InputFragment;
import com.arkaitzgarro.todolistfragment.model.ToDo;

public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener {

    private final String TODO = "TODO";

    private InputFragment.TODOItemListener listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            listFragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.listFragment);
        } catch (ClassCastException ex) {
            throw new ClassCastException(this.toString() + " must implement TODOItemListener interface");
        }
    }

    @Override
    public void addTodo(ToDo todo) {
        listFragment.addTodo(todo);
    }
}
