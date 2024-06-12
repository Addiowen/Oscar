package com.compulynx.compas.Requests;

import com.compulynx.compas.models.UserAssignedRights;

import java.util.List;

public class UserGroupReq {
    private Boolean active;
    private Long createdBy;
    private String groupCode;
    private String groupName;
    private Long id;

    //    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<UserAssignedRights> rights;

    public UserGroupReq() {
    }

//    public UserGroupReq(Boolean active, Long createdBy, String groupCode, String groupName, Long id) {
//        this.active = active;
//        this.createdBy = createdBy;
//        this.groupCode = groupCode;
//        this.groupName = groupName;
//        this.id = id;
//    }

    public UserGroupReq(Boolean active, Long createdBy, String groupCode, String groupName, Long id, List<UserAssignedRights> rightsList) {
        this.active = active;
        this.createdBy = createdBy;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.id = id;
        this.rights = rightsList;
    }

    public List<UserAssignedRights> getRights() {
        return rights;
    }

    public void setRights(List<UserAssignedRights> rightsList) {
        System.out.println("trying to set up rights...");
        System.out.println(rightsList);
        this.rights = rightsList;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserGroupReq{" +
                "active=" + active +
                ", createdBy=" + createdBy +
                ", groupCode='" + groupCode + '\'' +
                ", groupName='" + groupName + '\'' +
                ", id=" + id +
                ", rightsList=" + rights +
                '}';
    }
}
