package com.compulynx.compas.repositories;

import com.compulynx.compas.models.UserAssignedRights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserAssignedRightsRepository extends JpaRepository<UserAssignedRights, Long> {


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update roleupdate set status='A', updated_at=getDate(), updated_by=?2 where id=?1")
    void approveRoleRecord(
            Long id,
            String principal
    );


    @Transactional
    @Query(nativeQuery = true, value = "select * from userassignedrights where rightId=?1 AND GroupId=?2")
    Optional<UserAssignedRights> recordsByRightIdAndGroupId(Integer rightId, Integer groupId);

    // @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "INSERT INTO userassignedrights (AllowAdd, AllowView, AllowEdit, AllowDelete, CreatedBy, rightId, rightName, GroupId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
////    INSERT INTO roleupdate (AllowAdd, AllowDelete, AllowEdit, AllowView, CreatedBy, rightId, GroupId, rightName) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
//    Integer updateUserAssignedRightAddNew(Boolean allowAdd, Boolean allowView, Boolean allowEdit, Boolean allowDelete, Integer createdBy, Integer rightId, String rightName, UserGroup groupId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO userassignedrights (AllowAdd, AllowView, AllowEdit, AllowDelete, CreatedBy, rightId, rightName, GroupId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
//    INSERT INTO roleupdate (AllowAdd, AllowDelete, AllowEdit, AllowView, CreatedBy, rightId, GroupId, rightName) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    Integer addUserAssignedRight(Boolean allowAdd, Boolean allowView, Boolean allowEdit, Boolean allowDelete, Integer createdBy, Integer rightId, String rightName, Integer group_Id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "" +
            "DECLARE\n" +
            "    @allowAdd BIT,\n" +
            "    @allowEdit BIT,\n" +
            "    @allowView BIT,\n" +
            "    @allowDelete BIT,\n" +
            "    @rightId INT,\n" +
            "    @groupId INT,\n" +
            "    @createdBy INT,\n" +
            "    @rightName varchar(255)\n" +
            "\n" +
            "SET @allowAdd =?1\n" +
            "SET @allowDelete =?4\n" +
            "SET @allowEdit =?3\n" +
            "SET @allowView =?2\n" +
            "SET @rightId =?6\n" +
            "SET @groupId =?8\n" +
            "SET @createdBy =?5\n" +
            "SET @rightName =?7\n" +
            "IF @allowAdd IS NULL\n" +
            "    SET @allowAdd = (SELECT AllowAdd from userassignedrights WHERE GroupId = @groupId AND rightId = @rightId)\n" +
            "IF @allowView IS NULL\n" +
            "    SET @allowView = (SELECT AllowView from userassignedrights WHERE GroupId = @groupId AND rightId = @rightId)\n" +
            "IF @allowEdit IS NULL\n" +
            "    SET @allowEdit = (SELECT AllowEdit from userassignedrights WHERE GroupId = @groupId AND rightId = @rightId)\n" +
            "IF @allowDelete IS NULL\n" +
            "    SET @allowDelete = (SELECT AllowDelete from userassignedrights WHERE GroupId = @groupId AND rightId = @rightId)\n" +
            "UPDATE userassignedrights\n" +
            "set AllowAdd    = @allowAdd,\n" +
            "    AllowView   = @allowView,\n" +
            "    AllowEdit   = @allowEdit,\n" +
            "    AllowDelete = @allowDelete,\n" +
            "    CreatedBy   = @createdBy,\n" +
            "    rightId     = @rightId,\n" +
            "    rightName   = @rightName,\n" +
            "    GroupId     = @groupId\n" +
            "WHERE GroupId = @groupId\n" +
            "  AND rightId = @rightId;")
    Integer updateUserAssignedRight(Boolean allowAdd, Boolean allowView, Boolean allowEdit, Boolean allowDelete, Integer createdBy, Integer rightId, String rightName, Integer group_Id);


}
