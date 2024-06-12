package com.compulynx.compas.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "audit_logs")
public class AuditTrail extends BaseModel {

  @Column(name = "user_id")
  private long userId;

  @Column(name = "module")
  private String module;

  @Column(name = "date")
  private Date date;

  @Column(name = "action")
  private String action;

  @Column(name = "branch_id")
  private String branch;
 

  @Column(name = "remote_ip")
  private String remoteIP;

  @Column(name = "server_ip")
  private String serverIP;

  @Column(name = "previous_value", columnDefinition = "NVARCHAR(MAX)")
  private String previousValue;

  @Column(name = "new_value", columnDefinition = "NVARCHAR(MAX)")
  private String newValue;
 

  @Column(name = "action_status")
  private String actionStatus;

  @Column(name = "fail_reason", length = 500)
  private String failReason;

  @Column(name = "request_id")
  private String requestId;
  
  public String branchName;

  public AuditTrail() {
    super();
  }

public AuditTrail(long userId, String module, Date date, String action, String branch, String remoteIP, String serverIP,
		String previousValue, String newValue, String actionStatus, String failReason, String requestId) {
	super();
	this.userId = userId;
	this.module = module;
	this.date = date;
	this.action = action;
	this.branch = branch;
	this.remoteIP = remoteIP;
	this.serverIP = serverIP;
	this.previousValue = previousValue;
	this.newValue = newValue;
	this.actionStatus = actionStatus;
	this.failReason = failReason;
	this.requestId = requestId;
}





public long getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
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




public String getBranch() {
	return branch;
}

public void setBranch(String branch) {
	this.branch = branch;
}

public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getRemoteIP() {
    return remoteIP;
  }

  public void setRemoteIP(String remoteIP) {
    this.remoteIP = remoteIP;
  }

  public String getServerIP() {
    return serverIP;
  }

  public void setServerIP(String serverIP) {
    this.serverIP = serverIP;
  }

  public String getPreviousValue() {
    return previousValue;
  }

  public void setPreviousValue(String previousValue) {
    this.previousValue = previousValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public String getActionStatus() {
    return actionStatus;
  }

  public void setActionStatus(String actionStatus) {
    this.actionStatus = actionStatus;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

@Override
public String toString() {
	return "AuditTrail [userId=" + userId + ", module=" + module + ", date=" + date + ", action=" + action + ", branch="
			+ branch + ", remoteIP=" + remoteIP + ", serverIP=" + serverIP + ", previousValue=" + previousValue
			+ ", newValue=" + newValue + ", actionStatus=" + actionStatus + ", failReason=" + failReason
			+ ", requestId=" + requestId + ", branchName=" + branchName + "]";
}

  
}
