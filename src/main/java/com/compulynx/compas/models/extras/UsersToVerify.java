package com.compulynx.compas.models.extras;

import java.io.Serializable;
import java.util.Date;

public interface UsersToVerify extends Serializable {
	int getCounter();

	int getId();

	int getCreatedBy();

	String getFullName();

	String getGroupName();
	
	String getPhone();
	
	String getEmail();
	
	String getLastName();
	
	String getBranchName();
	
	String getFirstName();
	
	String getMiddleName();
	
	String getCreatedByName();

	Date getCreatedAt();

	Date getUpdatedAt();

	String getUpdateJson();

	String getUpdatedByName();

	String getUpdatedBy();



//	String getCreatedBy1();
}
