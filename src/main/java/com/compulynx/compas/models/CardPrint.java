package com.compulynx.compas.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CardPrint")
public class CardPrint extends BaseModel {
	
	@Column(name="AccountName")
    public String accountName;
	
	@Column(name="PrintedBy")
    public int printedBy;

	
	@Column(name="user_id")
    public int userId;
	
	@Column(name="branch_id")
	public int  branchId;
	
	@Column(name="DatePrinted")
    public Timestamp datePrinted;
	
	@Column(name="CardFormatId")
    public int cardFormatId;
	
	@Column(name="PrinterTypeId")
    public String printerTypeId;
	
	@Column(name  ="valid_thru")
	public String valid_thru;
	
	@Column(name="status")
	public boolean active;
	
	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}

	public String BranchName;
	

	public CardPrint() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CardPrint(String accountName, String BranchName, Timestamp datePrinted) {
		super();
		this.accountName = accountName;
		this.BranchName=BranchName;
		this.datePrinted = datePrinted;
	}



	public String getValid_thru() {
		return valid_thru;
	}



	public void setValid_thru(String valid_thru) {
		this.valid_thru = valid_thru;
	}



	public String getAccountName() {
		return accountName;
	}

	public String getPrinterTypeId() {
		return printerTypeId;
	}

	public void setPrinterTypeId(String printerTypeId) {
		this.printerTypeId = printerTypeId;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getBranchId() {
		return branchId;
	}



	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}



	public int getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(int printedBy) {
		this.printedBy = printedBy;
	}

	public Timestamp getDatePrinted() {
		return datePrinted;
	}

	public void setDatePrinted(Timestamp timestamp) {
		this.datePrinted = timestamp;
	}

	public int getCardFormatId() {
		return cardFormatId;
	}

	public void setCardFormatId(int cardFormatId) {
		this.cardFormatId = cardFormatId;
	}
	
}
