package com.compulynx.compas.models.extras;

import java.io.Serializable;
import java.util.Date;

public interface UsersToActivate extends Serializable {
	int getCounter();

	int getId();

	String getCreatedBy();

	String getFullName();

	String getGroupName();

	Date getCreatedAt();
}
