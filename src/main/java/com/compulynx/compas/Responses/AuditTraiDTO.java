package com.compulynx.compas.Responses;

import java.util.Date;

import com.compulynx.compas.models.Branch;

public class AuditTraiDTO {

	private long userId;
	private String module;
	private Date date;
	private String action;
	private String username;
	private String branchName;
	private String remoteIp;
	private String actionStatus;
	private String failReason;
	private String branch;
	
	public AuditTraiDTO() {
	}
		
	public AuditTraiDTO(long userId, String module, Date date, String action, 
			String remoteIp, String actionStatus,String branch,String branchName,String failReason) {
		super();
		this.userId = userId;
		this.module = module;
		this.date = date;
		this.action = action;
		this.remoteIp = remoteIp;
		this.actionStatus = actionStatus;
		this.branch=branch;
		this.branchName=branchName;
		this.failReason=failReason;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}



	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
	public String toString() {
		return "AuditTraiDTO [userId=" + userId + ", module=" + module + ", date=" + date + ", action=" + action
				+ ", username=" + username + ", remoteIp=" + remoteIp + ", actionStatus=" + actionStatus + ", branch="
				+ branch + "]";
	}


	
}
