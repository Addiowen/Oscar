package com.compulynx.compas.security;

import java.util.Set;
public class UserMenuSession {
	private int sessionId;
	private String sessionName;
	private String userGroupId;
	private int respCode;
	Set<?> MenuHeaderList;
	public UserMenuSession(int sessionId, String sessionName,
			String userGroupId, int respCode, Set<?> menuHeaderList) {
		super();
		this.sessionId = sessionId;
		this.sessionName = sessionName;
		this.userGroupId = userGroupId;
		this.respCode = respCode;
		MenuHeaderList = menuHeaderList;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public int getRespCode() {
		return respCode;
	}
	
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public Set<?> getMenuHeaderList() {
		return MenuHeaderList;
	}
	public void setMenuHeaderList(Set<?> menuHeaderList) {
		MenuHeaderList = menuHeaderList;
	}
	
	
}
