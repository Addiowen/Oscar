package com.compulynx.compas.models.extras;

public interface UserGroupRightsAccess{
	Long getRightId();
	String getRightName();
	Boolean getAllowView();
	Boolean getAllowAdd();
	Boolean getAllowEdit();
	Boolean getAllowDelete();
}
