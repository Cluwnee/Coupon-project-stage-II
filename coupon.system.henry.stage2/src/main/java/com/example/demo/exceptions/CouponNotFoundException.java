package com.example.demo.exceptions;

public class CouponNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	public CouponNotFoundException() {
		super("ERROR: Coupon not found");
	}

}
