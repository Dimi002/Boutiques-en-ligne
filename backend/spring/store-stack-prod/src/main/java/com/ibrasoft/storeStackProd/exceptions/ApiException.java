package com.ibrasoft.storeStackProd.exceptions;

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@ibrasoft.eu
*/
public class ApiException extends Exception {
	private String code;
	private String message;
	private String status;

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String code, String message, String status) {
		super(message);
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
