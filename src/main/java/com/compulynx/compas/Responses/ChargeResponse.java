package com.compulynx.compas.Responses;

public class ChargeResponse {

	private boolean status;
	private String statusCode;
	private float charge;
	private Float exciseDuty;
	
	public ChargeResponse() {
	}

	public ChargeResponse(boolean status, String statusCode, float charge, Float exciseDuty) {
		this.status = status;
		this.statusCode = statusCode;
		this.charge = charge;
		this.exciseDuty = exciseDuty;
	}
	
	public Float getExciseDuty() {
		return exciseDuty;
	}

	public void setExciseDuty(Float exciseDuty) {
		this.exciseDuty = exciseDuty;
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

	public float getCharge() {
		return charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	@Override
	public String toString() {
		return "ChargeResponse [status=" + status + ", statusCode=" + statusCode + ", charge=" + charge + "]";
	}
	
}
