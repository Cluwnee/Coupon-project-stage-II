package com.example.demo.exceptions;

public class BoughtCouponException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;

	public BoughtCouponException() {
		super("ERROR: You already own this coupon");
	}

}
