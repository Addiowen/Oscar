package com.compulynx.compas.models;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "usergroupsmaster")
public class UserGroup extends BaseModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "GroupCode")
  private String groupCode;

  @Column(name = "GroupName")
  private String groupName;

  @Column(name = "GroupTypeID")
  private int groupTypeID;

  @Column(name = "Active")
  private boolean active;

  @NotFound(action= NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "CreatedBy")
  private User createdBy;

  @NotFound(action=NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "modified_by")
  private User modifiedBy;

  @Column(name = "CreatedOn")
  private Date createdOn;

  @Column(name = "modified_on")
  private Date modifiedOn;

  @ManyToMany(mappedBy = "groups")
  private Set<RightMaster> roles;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL /*, orphanRemoval = true */)
  @JoinColumn(name = "GroupId")
  private List<UserAssignedRights> rights;

  protected UserGroup() {
    super();
  }

  public UserGroup(
      String groupCode,
      String groupName,
      int groupTypeID,
      boolean active,
      User createdBy,
      List<UserAssignedRights> rights,
      User modifiedBy,
      Date createdOn,
      Date modifiedOn) {
    super();
    this.groupCode = groupCode;
    this.groupName = groupName;
    this.groupTypeID = groupTypeID;
    this.active = active;
    this.createdBy = createdBy;
    this.rights = rights;
    this.modifiedBy = modifiedBy;
    this.createdOn = createdOn;
    this.modifiedOn = modifiedOn;
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

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  public List<UserAssignedRights> getRights() {
    return rights;
  }

  public void addRight(UserAssignedRights right) {
    right.setGroupId(this);
    rights.add(right);
  }

  public void removeRight(UserAssignedRights rights) {
    rights.setGroupId(null);
    this.rights.remove(rights);
  }

  public void removeAllRights() {
    rights.forEach(rights -> rights.setGroupId(null));
    this.rights.clear();
  }

  public Set<RightMaster> getRoles() {
    return roles;
  }

  @Override
  public String toString() {
    return "UserGroup{" +
            "groupCode='" + groupCode + '\'' +
            ", groupName='" + groupName + '\'' +
            ", groupTypeID=" + groupTypeID +
            ", active=" + active +
            ", createdBy=" + createdBy +
            ", modifiedBy=" + modifiedBy +
            ", createdOn=" + createdOn +
            ", modifiedOn=" + modifiedOn +
            ", roles=" + roles +
            ", rights=" + rights +
            '}';
  }
}
