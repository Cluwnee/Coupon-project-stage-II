package com.example.demo.exceptions;

public class NoCouponsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	public NoCouponsException() {
		super("Oops! It seems a little empty here... Maybe add some coupons?");
	}

}