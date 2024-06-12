package com.compulynx.compas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "userassignedrights")
public class UserAssignedRights extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean AllowView;
    private Boolean AllowAdd;
    private Boolean AllowEdit;
    private Boolean AllowDelete;
    private int CreatedBy;
    private int rightId;
    private String rightName;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    private UserGroup GroupId;

    protected UserAssignedRights() {
        super();
    }

    public UserAssignedRights(Boolean allowView, Boolean allowAdd, Boolean allowEdit, Boolean allowDelete,
                              int createdBy, int rightId, String rightName, UserGroup groupId) {
        super();
        AllowView = allowView;
        AllowAdd = allowAdd;
        AllowEdit = allowEdit;
        AllowDelete = allowDelete;
        CreatedBy = createdBy;
        this.rightId = rightId;
        this.rightName = "'" + rightName + "'";
        GroupId = groupId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Boolean isAllowView() {
        return AllowView;
    }

    public void setAllowView(Boolean allowView) {
        AllowView = allowView;
    }

    public Boolean isAllowAdd() {
        return AllowAdd;
    }

    public void setAllowAdd(Boolean allowAdd) {
        AllowAdd = allowAdd;
    }

    public Boolean isAllowEdit() {
        return AllowEdit;
    }

    public void setAllowEdit(Boolean allowEdit) {
        AllowEdit = allowEdit;
    }

    public Boolean isAllowDelete() {
        return AllowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        AllowDelete = allowDelete;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public int getRightId() {
        return rightId;
    }

    public void setRightId(int rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @JsonIgnore
    public UserGroup getGroupId() {
        return GroupId;
    }

    public void setGroupId(UserGroup groupId) {
        GroupId = groupId;
    }

    @Override
    public String toString() {
        return "{" +
                "'AllowView':" + AllowView +
                ", 'AllowAdd':" + AllowAdd +
                ", 'AllowEdit':" + AllowEdit +
                ", 'AllowDelete':" + AllowDelete +
                ", 'CreatedBy':" + CreatedBy +
                ", 'rightId':" + rightId +
                ", 'rightName':" + rightName +
                ", 'GroupId':" + GroupId +
                "}";
    }
}