package com.compulynx.compas.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CardPrint")
public class IssuedCard extends BaseModel {
	
	@Column(name="AccountName")
    public String accountName;
	
	@Column(name="DatePrinted")
    public Timestamp datePrinted;
	
	@Column(name="PrinterTypeId")
    public String printerTypeId;
	
	public String BranchName;
	
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "PrintedBy", referencedColumnName = "id")
	  @NotFound(action = NotFoundAction.IGNORE)
	  private User user;

	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "branch_id", referencedColumnName = "branchCode")
	  @NotFound(action = NotFoundAction.IGNORE)
	  private Branch branch;
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "CardFormatId", referencedColumnName = "id")
	  @NotFound(action = NotFoundAction.IGNORE)
	  private CardType cardType;

	public IssuedCard() {
		super();
		// TODO Auto-generated constructor stub
	}


	public IssuedCard(String accountName, Timestamp datePrinted, String printerTypeId, String branchName, User user,
			Branch branch, CardType cardType) {
		super();
		this.accountName = accountName;
		this.datePrinted = datePrinted;
		this.printerTypeId = printerTypeId;
		BranchName = branchName;
		this.user = user;
		this.branch = branch;
		this.cardType = cardType;
	}




	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public Timestamp getDatePrinted() {
		return datePrinted;
	}

	public void setDatePrinted(Timestamp datePrinted) {
		this.datePrinted = datePrinted;
	}


	public String getPrinterTypeId() {
		return printerTypeId;
	}

	public void setPrinterTypeId(String printerTypeId) {
		this.printerTypeId = printerTypeId;
	}

	public String getBranchName() {
		return BranchName;
	}

	public void setBranchName(String branchName) {
		BranchName = branchName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}


	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "IssuedCard [accountName=" + accountName + ", datePrinted=" + datePrinted + ", printerTypeId="
				+ printerTypeId + ", BranchName=" + BranchName + ", user=" + user + ", branch=" + branch + ", cardType="
				+ cardType + "]";
	}


	
}
