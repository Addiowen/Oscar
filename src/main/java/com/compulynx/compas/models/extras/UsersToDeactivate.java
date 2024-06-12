package com.compulynx.compas.models.extras;

import java.io.Serializable;
import java.util.Date;

public interface UsersToDeactivate extends Serializable {
	int getCounter();

	int getId();

	String getCreatedBy();

	String getFullName();

	String getGroupName();

	Date getCreatedAt();
}
