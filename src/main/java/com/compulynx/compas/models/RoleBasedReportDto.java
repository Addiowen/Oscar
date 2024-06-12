package com.compulynx.compas.models;

import java.util.Arrays;
import java.util.Date;

public class RoleBasedReportDto {

    private int id;
    private String groupCode;
    private String groupName;
    private int groupTypeID;
    private boolean active;
    private User createdBy;
    private User modifiedBy;
    private Date createdOn;
    private Date modifiedOn;
    private Role[] roles;

  public RoleBasedReportDto() {
  }

  public RoleBasedReportDto(int id, String groupCode, String groupName, int groupTypeID, boolean active, User createdBy, User modifiedBy, Date createdOn, Date modifiedOn, Role[] roles) {
    this.id = id;
    this.groupCode = groupCode;
    this.groupName = groupName;
    this.groupTypeID = groupTypeID;
    this.active = active;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.createdOn = createdOn;
    this.modifiedOn = modifiedOn;
    this.roles = roles;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGroupCode() {
    return groupCode;
  }

  public void setGroupCode(String groupCode) {
    this.groupCode = groupCode;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public int getGroupTypeID() {
    return groupTypeID;
  }

  public void setGroupTypeID(int groupTypeID) {
    this.groupTypeID = groupTypeID;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public Date getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  public Role[] getRoles() {
    return roles;
  }

  public void setRoles(Role[] roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "RoleBasedReportDto{" +
            "id=" + id +
            ", groupCode='" + groupCode + '\'' +
            ", groupName='" + groupName + '\'' +
            ", groupTypeID=" + groupTypeID +
            ", active=" + active +
            ", createdBy=" + createdBy +
            ", modifiedBy=" + modifiedBy +
            ", createdOn=" + createdOn +
            ", modifiedOn=" + modifiedOn +
            ", roles=" + Arrays.toString(roles) +
            '}';
  }
}

