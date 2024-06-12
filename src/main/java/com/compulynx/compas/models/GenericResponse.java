package com.compulynx.compas.models;

public class GenericResponse {

	private boolean status;
	private Object result;
	private Object Data;
	private String response_code;
	
	public GenericResponse() {
	}
		
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public GenericResponse(boolean status, Object result) {
		this.status = status;
		this.result = result;
	}
	
	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	@Override
	public String toString() {
		return "GenericResponse [status=" + status + ", result=" + result + "]";
	}
	
	
}
