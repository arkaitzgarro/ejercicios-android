package com.arkaitzgarro.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arkaitzgarro.calculator.listeners.NumberOnClickListener;
import com.arkaitzgarro.calculator.listeners.OperationOnClickListener;
import com.arkaitzgarro.calculator.logic.CalcLogic;

import java.util.ArrayList;

/**
 * Created by Arkaitz Garro on 18/03/15.
 */
public class MainActivity extends ActionBarActivity
        implements NumberOnClickListener.NumberListenerInterface,
        OperationOnClickListener.OperationListenerInterface {

    private final String CALC = "CALC";

    private ArrayList<Button> numberButtons;
    private ArrayList<Button> operationButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberButtons = new ArrayList<>();
        operationButtons = new ArrayList<>();

        getButtonsFromLayout();
        addEventListeners();
    }

    /**
     * Get all buttons from layout
     */
    private void getButtonsFromLayout() {
        String[] numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Zero", "Point"};

        String id;
        Button btn;

        for (int i = 0; i < numbers.length; i++) {
            id = "btn".concat(numbers[i]);

            btn = (Button) findViewById(getResources().getIdentifier(id, "id", getPackageName()));
            numberButtons.add(btn);
        }

        String[] operations = {"Add", "Sub", "Multiply", "Divide"};

        for (int i = 0; i < numbers.length; i++) {
            id = "btn".concat(numbers[i]);

            btn = (Button) findViewById(getResources().getIdentifier(id, "id", getPackageName()));
            operationButtons.add(btn);
        }
    }

    /**
     * Add click listeners to buttons
     */
    private void addEventListeners() {
        View.OnClickListener numberOnClickListener = new NumberOnClickListener(this);
        View.OnClickListener operationOnClickListener = new OperationOnClickListener(this);

        // Add listeners to number buttons
        for (int i = 0; i < numberButtons.size(); i++) {
            numberButtons.get(i).setOnClickListener(numberOnClickListener);
        }

        // Add listeners to operation buttons
        for (int i = 0; i < operationButtons.size(); i++) {
            operationButtons.get(i).setOnClickListener(operationOnClickListener);
        }
    }

    @Override
    public void setNumber(String number) {
        Log.d(CALC, number);
    }

    @Override
    public void setOperation(String op) {
        Log.d(CALC, op);
    }
}
