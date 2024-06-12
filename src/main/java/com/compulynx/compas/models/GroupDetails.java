package com.compulynx.compas.models;

public class GroupDetails {

    private String groupName;
    private String groupTypeID;
    private String id;
    private String groupCode;

    public GroupDetails(String groupName, String groupTypeID, String id, String groupCode) {
        this.groupName = groupName;
        this.groupTypeID = groupTypeID;
        this.id = id;
        this.groupCode = groupCode;
    }

    public GroupDetails() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTypeID() {
        return groupTypeID;
    }

    public void setGroupTypeID(String groupTypeID) {
        this.groupTypeID = groupTypeID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Override
    public String toString() {
        return "GroupDetails{" +
                "groupName='" + groupName + '\'' +
                ", groupTypeID='" + groupTypeID + '\'' +
                ", id='" + id + '\'' +
                ", groupCode='" + groupCode + '\'' +
                '}';
    }
}
