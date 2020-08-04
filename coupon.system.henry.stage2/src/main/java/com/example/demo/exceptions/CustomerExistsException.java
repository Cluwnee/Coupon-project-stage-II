package com.example.demo.exceptions;

public class CustomerExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;

	public CustomerExistsException() {
		super("ERROR: Customer already exists");
	}

}
