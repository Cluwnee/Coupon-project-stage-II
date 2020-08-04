package com.example.demo.exceptions;

public class CompanyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	public CompanyExistsException() {
		super("ERROR: Company already exists");
	}

}
