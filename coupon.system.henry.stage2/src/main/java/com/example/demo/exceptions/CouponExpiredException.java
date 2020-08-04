package com.example.demo.exceptions;

public class CouponExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public CouponExpiredException() {
		super("ERROR: Coupon has expired");
	}

}
