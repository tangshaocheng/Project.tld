package com.energysh.egame.exception;

/**
 * 支付异常类，code 关联payErrorCodes.properties 用于页面错误显示
 * 
 * @author jin.xiong
 * 
 */
public class PayException extends Exception {

	public static final long serialVersionUID = 0x01;
	private String code;
	private String msg;

	public PayException() {
	}

	public PayException(String code) {
		super(code);
		this.code = code;
	}

	public PayException(String code, String msg) {
		super(code + ": " + msg);
		this.msg = msg;
		this.code = code;
	}

	public PayException(String code, Throwable cause) {
		super(code, cause);
		this.code = code;
	}

	public PayException(String code, String msg, Throwable cause) {
		super(code + ": " + msg, cause);
		this.msg = msg;
		this.code = code;
	}

	public PayException(Throwable cause) {
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