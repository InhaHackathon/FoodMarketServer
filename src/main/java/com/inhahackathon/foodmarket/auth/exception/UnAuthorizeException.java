package com.inhahackathon.foodmarket.auth.exception;

public class UnAuthorizeException extends NullPointerException {

	private static final String DEFAULT_MESSAGE = "UnAuthorize Request!!";

	public UnAuthorizeException() {
		super(DEFAULT_MESSAGE);
	}

	public UnAuthorizeException(String s) {
		super(s);
	}

}
