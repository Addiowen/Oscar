package com.compulynx.compas.models;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.Nullable;

/**
 * @author KENSON
 *
 */
@Entity
@Table(name="BranchMaster")
public class Branch extends BaseModel implements Serializable {
	
	
	@Column(name="branchCode")
	private String branchCode;
	
	@Column(name="BranchName")
	private String BranchName;
	
	@Nullable
	@Column(name="BranchAddress")
	private String branchAddress;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	@Column(name="created_on")
	public Timestamp createdOn;
	
	@Nullable
	@Column(name="status")
	private boolean active;
	
	public Branch() {
	}

	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return BranchName;
	}
	public void setBranchName(String branchName) {
		BranchName = branchName;
	}


	
	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public boolean getActive() {
		return active;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}


}
