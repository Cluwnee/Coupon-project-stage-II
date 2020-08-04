package com.example.demo.exceptions;

public class CompanyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyNotFoundException() {
		super("ERROR: Company not found");
	}

}
