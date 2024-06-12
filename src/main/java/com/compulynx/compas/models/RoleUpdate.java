package com.compulynx.compas.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "roleupdate")
public class RoleUpdate {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_json", columnDefinition = "NVARCHAR(MAX)")
    private String updateJSON;

    public RoleUpdate(String groupId, String status, Date createdAt, String createdBy, Date updatedAt, String updatedBy, String updateJSON) {
        this.groupId = groupId;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.updateJSON = updateJSON;
    }

    public RoleUpdate() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdateJSON() {
        return updateJSON;
    }

    public void setUpdateJSON(String updateJSON) {
        this.updateJSON = updateJSON;
    }


    @Override
    public String toString() {
        return "RoleUpdate{" +
                "Id=" + Id +
                ", groupId='" + groupId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateJSON='" + updateJSON + '\'' +
                '}';
    }
}
