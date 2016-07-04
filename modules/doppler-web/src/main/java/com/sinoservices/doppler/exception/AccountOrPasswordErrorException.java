package com.sinoservices.doppler.exception;


/**
 * 账号或密码错误异常类
 * 
 */
public class AccountOrPasswordErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	private static String ERROR_ACCOUNT_LOGIN_LOGINERROR = "账号或者密码错误";

	
	public AccountOrPasswordErrorException() {
		super(ERROR_ACCOUNT_LOGIN_LOGINERROR);
	}

	public AccountOrPasswordErrorException(String msg) {
		super(msg);
	}

	public AccountOrPasswordErrorException(Throwable cause) {
		super(cause);
	}

	public AccountOrPasswordErrorException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
