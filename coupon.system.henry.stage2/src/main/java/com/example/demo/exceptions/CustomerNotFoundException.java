package com.example.demo.exceptions;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public CustomerNotFoundException() {
		super("ERROR: Customer not found");
	}

}
