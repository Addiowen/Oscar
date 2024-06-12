package com.compulynx.compas.Responses;

public class SuccessResponse {

	private boolean status;
	private String statusCode;
	private String message;
	
	public SuccessResponse() {
	}

	public SuccessResponse(boolean status, String statusCode, String message) {
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SuccessResponse [status=" + status + ", statusCode=" + statusCode + ", message=" + message + "]";
	}
	
}
