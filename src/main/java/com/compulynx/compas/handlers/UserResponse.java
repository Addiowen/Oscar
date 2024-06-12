package com.compulynx.compas.handlers;

import com.compulynx.compas.models.User;

public class UserResponse {
	public static final String  APIV="1.0";
	private int respCode;
	private String respMessage;
	private boolean status;
	private String version;
	private User model;

	private String jwtToken;

	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getRespMessage() {
		return respMessage;
	}
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public User getModel() {
		return model;
	}
	public void setModel(User model) {
		this.model = model;
	}
	public static String getApiv() {
		return APIV;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public UserResponse(String respMessage, int respCode, boolean status, String version, User model) {
		super();
		this.respMessage =respMessage;
		this.respCode=respCode;
		this.status=status;
		this.version=version;	
		this.model = model;
	}
	public UserResponse(String version, int respCode, boolean status, String respMessage) {
		this.version=version;
		this.respCode=respCode;
		this.status=status;
		this.respMessage =respMessage;
	}

	public UserResponse(String respMessage, int respCode, boolean status, String version, User model, String jwt) {
		super();
		this.respMessage =respMessage;
		this.respCode=respCode;
		this.status=status;
		this.version=version;
		this.model = model;
		this.jwtToken = jwt;
	}
	
}
