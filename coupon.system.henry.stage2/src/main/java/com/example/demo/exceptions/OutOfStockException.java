package com.example.demo.exceptions;

public class OutOfStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;

	public OutOfStockException() {
		super("ERROR: Coupon is out of stock");
	}

}
