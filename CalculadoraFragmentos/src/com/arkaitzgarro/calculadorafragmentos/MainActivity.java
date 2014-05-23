package com.arkaitzgarro.calculadorafragmentos;

import com.arkaitzgarro.calculadora.Calc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements KeyboardFragment.ICalculator {
	
	private Calc calc = null;
	private TextView display = null;
	private StringBuilder numberDisplayed = null;
	private Double resultNumber = 0.0;
	protected String storedNumber;
	
	private final String SUM = "SUM";
	private final String MUL = "MUL";
	private final String DIV = "DIV";
	private final String RES = "RES";
	
	private String opActual = null;
	private boolean clear = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		calc = new Calc();
		numberDisplayed = new StringBuilder(resultNumber.toString());
	}

	@Override
	public void setNumber(String n) {
		Log.d("CALC", "setNumber("+n+")");
		
		if(clear) {
			numberDisplayed.setLength(0);
			clear = false;
		}
		
		numberDisplayed.append(n);
		
		updateTextField();
	}

	@Override
	public void setOp(String op) {
		Log.d("CALC", "setOp("+op+")");
		
	}

}
