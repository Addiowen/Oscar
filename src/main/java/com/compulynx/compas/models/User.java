package com.compulynx.compas.models;

import java.util.Date;
import javax.persistence.*;

/**
 * @author KENSON
 *
 */
@Entity
@Table(name = "users")
public class User extends BaseModel {
	
  public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

@Column(name = "fullname")
 private String fullName;
	  
  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;
  
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password")
  //	@JsonIgnore
  private String password;

  @Column(name = "phone")
  private String phone;



  @Column(name = "other_names", nullable = true)
  private String otherNames;

  @Column(name = "group_id")
  private int group;

  @Column(name = "created_by", nullable = true)
  private int createdBy;

  @Column(name = "status")
  private boolean status = false;

  @Column(name = "verified")
  private String approved;

  @Column(name = "verified_by", nullable = true)
  private int approvedBy;

  @Column(name = "verified_on")
  private Date approvedOn;

  @Column(name = "first_loggin")
  private Boolean firstLoggin;

  @Column(name = "logged_in")
  private int logged_in;

  // column branch
  @Column(name = "branchId")
  private String branchId;


  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "password_last_update")
  private Date passwordUpdate;


  @Column(name = "deleted")
  private Boolean deleted;

  @Column(name = "updated_by", nullable = true)
  private Integer updatedBy;

  @Column(name = "updated_at")
  private Date updatedAt;

  @Lob
  @Column(name = "update_json")
  private String updateJson;

  @Lob
  @Column(name = "updated_user_json")
  private String updatedUserJson;


  @Column(name = "update_status")
  private String updatedStatus;

  @Column(name = "usedPasswords")
  private String usedPasswords;

  public User() {
    super();
  }


  public String getMiddleName() {
	return middleName;
}


public void setMiddleName(String middleName) {
	this.middleName = middleName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getUpdatedStatus() {
    return updatedStatus;
  }

  public void setUpdatedStatus(String updatedStatus) {
    this.updatedStatus = updatedStatus;
  }

  public String getUpdatedUserJson() {
    return updatedUserJson;
  }

  public void setUpdatedUserJson(String updatedUserJson) {
    this.updatedUserJson = updatedUserJson;
  }

  public String getUpdateJson() {
    return updateJson;
  }

  public void setUpdateJson(String updateJson) {
    this.updateJson = updateJson;
  }

  public Integer getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Boolean getFirstLoggin() {
    return firstLoggin;
  }

  public void setFirstLoggin(Boolean firstLoggin) {
    this.firstLoggin = firstLoggin;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getPasswordUpdate() {
    return passwordUpdate;
  }

  public void setPasswordUpdate(Date passwordUpdate) {
    this.passwordUpdate = passwordUpdate;
  }

  public String getEmail() {
    return email;
  }



  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) throws Exception {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }



  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getOtherNames() {
    return otherNames;
  }

  public void setOtherNames(String otherNames) {
    this.otherNames = otherNames;
  }

  public int getGroup() {
    return group;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getApproved() {
    return approved;
  }

  public void setApproved(String approved) {
    this.approved = approved;
  }

  public int getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(int approvedBy) {
    this.approvedBy = approvedBy;
  }

  public Date getApprovedOn() {
    return approvedOn;
  }

  public void setApprovedOn(Date approvedOn) {
    this.approvedOn = approvedOn;
  }

  //	public List<TellerBio> getBios() {
  //		return bios;
  //	}
  //
  //	public void addBio(TellerBio bio) {
  //		bio.setUser(this);
  //		bios.add(bio);
  //	}

  public int getLogged_in() {
    return logged_in;
  }

  public void setLogged_in(int logged_in) {
    this.logged_in = logged_in;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public String getUsedPasswords() {
    return usedPasswords;
  }

  public void setUsedPasswords(String usedPasswords) {
    this.usedPasswords = usedPasswords;
  }

}
