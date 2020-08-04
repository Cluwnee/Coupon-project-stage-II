package com.example.demo.exceptions;

public class WrongUserDetailsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	public WrongUserDetailsException() {
		super("Incorrect email or password, try again.");
	}

}
