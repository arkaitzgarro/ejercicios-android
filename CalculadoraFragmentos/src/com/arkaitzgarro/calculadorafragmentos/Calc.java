package com.arkaitzgarro.calculadorafragmentos;

/**
 * @author arkaitz
 *
 */
public class Calc {
	
	public Calc() {
		
	}

	public double sum(double a, double b) {
		return a + b;
	}

	public String devide(double a, double b) throws ArithmeticException {
		if (b == 0) {
			//return ctx.getString(ctx.getResources().getIdentifier("divided_by_zero", "string", "com.starky.calc"));
			throw new ArithmeticException("Division by zero!");
		} else {
			Double c = a / b;
			return c.toString();
		}
	}

	public double difference(double a, double b) {
		return a - b;
	}

	public double product(double a, double b) {
		return a * b;
	}
	
}
