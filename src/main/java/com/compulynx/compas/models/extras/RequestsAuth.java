package com.compulynx.compas.models.extras;

public class RequestsAuth {
	private String apiKey;
	private String username;
	private String password;
	private String action;
	private String hashString;
	private boolean status;
	public RequestsAuth(String apiKey, String username, String password, String action, String hashString,
			boolean status) {
		super();
		this.apiKey = apiKey;
		this.username = username;
		this.password = password;
		this.action = action;
		this.hashString = hashString;
		this.status = status;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getHashString() {
		return hashString;
	}
	public void setHashString(String hashString) {
		this.hashString = hashString;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
