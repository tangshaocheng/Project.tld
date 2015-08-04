package com.energysh.egame.exception;

public class AppBizException extends Exception {

	public static final long serialVersionUID = 0x01;

	private String code;
	private String msg;

	public AppBizException() {
	}

	public AppBizException(String code) {
		super(code);
		this.code = code;
	}

	public AppBizException(String code, String msg) {
		super(code + ": " + msg);
		this.code = code;
		this.msg = msg;
	}

	public AppBizException(String code, Throwable cause) {
		super(code, cause);
		this.code = code;
	}

	public AppBizException(String code, String msg, Throwable cause) {
		super(code + ": " + msg, cause);
		this.code = code;
		this.msg = msg;
	}

	public AppBizException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}