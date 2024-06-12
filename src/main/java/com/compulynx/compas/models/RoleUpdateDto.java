package com.compulynx.compas.models;

import java.util.Date;

public class RoleUpdateDto {

  private Integer Id;
  private String groupId;
  private String groupName;
  private String groupCode;

  private String status;
  private Date createdAt;
  private String createdBy;
  private Date updatedAt;
  private String updatedBy;
  private String rights;


  public RoleUpdateDto() {
  }

  public RoleUpdateDto(Integer id, String groupId, String groupName, String groupCode, String status, Date createdAt, String createdBy, Date updatedAt, String updatedBy, String rights) {
    Id = id;
    this.groupId = groupId;
    this.groupName = groupName;
    this.groupCode = groupCode;
    this.status = status;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
    this.rights = rights;
  }


  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupCode() {
    return groupCode;
  }

  public void setGroupCode(String groupCode) {
    this.groupCode = groupCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getRights() {
    return rights;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }
}
