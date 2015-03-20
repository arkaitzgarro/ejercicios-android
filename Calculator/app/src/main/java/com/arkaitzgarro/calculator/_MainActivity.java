package com.arkaitzgarro.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.arkaitzgarro.calculator.logic.CalcLogic;

public class _MainActivity extends ActionBarActivity {

    private final String CALC = "CALC";

    private TextView display;

    private ArrayList<Button> numberButtons;
    private ArrayList<Button> operationButtons;

    private final String ADD = "ADD";
    private final String MUL = "MUL";
    private final String SUB = "SUB";
    private final String DIV = "DIV";

    private CalcLogic calc;
    private StringBuilder numberDisplayed;
    private String actualOP, previousOP;
    private Boolean clear;

    private View.OnClickListener numberListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            setNumber(btn.getText().toString());
        }
    };

    private View.OnClickListener operationListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            setOperation(btn.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clear = false;
        calc = new CalcLogic();
        actualOP = "";
        previousOP = "";
        numberDisplayed = new StringBuilder("");

        numberButtons = new ArrayList<>();
        operationButtons = new ArrayList<>();

        getViewElements();
        addEventListeners();
    }

    /**
     * Get view buttons
     */
    private void getViewElements() {
        Button btnOne, btnTwo, btnThree, btnFour, btnFive;
        Button btnSix, btnSeven, btnEight, btnNine, btnZero, btnPoint;
        Button btnAdd, btnSub, btnMultiply, btnDivide, btnEqual;

        display = (TextView) findViewById(R.id.lblDisplay);

        // Get views
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnPoint = (Button) findViewById(R.id.btnPoint);

        // Group views
        numberButtons.add(btnOne);
        numberButtons.add(btnTwo);
        numberButtons.add(btnThree);
        numberButtons.add(btnFour);
        numberButtons.add(btnFive);
        numberButtons.add(btnSix);
        numberButtons.add(btnSeven);
        numberButtons.add(btnEight);
        numberButtons.add(btnNine);
        numberButtons.add(btnZero);
        numberButtons.add(btnPoint);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnEqual = (Button) findViewById(R.id.btnEqual);

        operationButtons.add(btnAdd);
        operationButtons.add(btnSub);
        operationButtons.add(btnMultiply);
        operationButtons.add(btnDivide);
        operationButtons.add(btnEqual);
    }

    /**
     * Assign listeners to views
     */
    private void addEventListeners() {
        for (int i = 0; i < numberButtons.size(); i++) {
            numberButtons.get(i).setOnClickListener(numberListener);
        }

        for (int i = 0; i < operationButtons.size(); i++) {
            operationButtons.get(i).setOnClickListener(operationListener);
        }
    }

    private void updateDisplay(StringBuilder result) {
        Log.d(CALC, result.toString());

        display.setText(result.toString());
    }

    private void setNumber(String number) {
        Log.d(CALC, number);

        if (clear) {
            numberDisplayed.setLength(0);
            clear = false;
        }

        numberDisplayed.append(number);

        updateDisplay(numberDisplayed);
    }

    private void setOperation(String op) {
        Log.d(CALC, op);

        previousOP = actualOP;

        switch (op) {
            case "+": actualOP = ADD; break;
            case "-": actualOP = SUB; break;
            case "*": actualOP = MUL; break;
            case "/": actualOP = DIV; break;
        }

        clear = true;

        processCalc();
    }

    private void processCalc() {
        double result = Double.parseDouble(numberDisplayed.toString());

        switch (previousOP) {
            case ADD:
                result = calc.add(numberDisplayed.toString());
                break;
            case SUB:
                result =  calc.subtract(numberDisplayed.toString());
                break;
            case MUL:
                result =  calc.multiply(numberDisplayed.toString());
                break;
            case DIV:
                result =  calc.divide(numberDisplayed.toString());
                break;
            default:
                calc.setTotal(Double.parseDouble(numberDisplayed.toString()));
                break;
        }

        Log.d(CALC, String.valueOf(result));

        numberDisplayed.setLength(0);
        numberDisplayed.append(String.valueOf(result));

        Log.d(CALC, numberDisplayed.toString());

        updateDisplay(numberDisplayed);
    }
}
