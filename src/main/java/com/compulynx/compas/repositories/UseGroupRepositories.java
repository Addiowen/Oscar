package com.compulynx.compas.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.compulynx.compas.models.extras.UserGroupRightsAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.UserGroup;
import com.compulynx.compas.models.extras.UserGroupRights;

@Repository
public interface UseGroupRepositories extends JpaRepository<UserGroup, Long> {


//  @Override
//  List<UserGroup> findAll();

  @Query("from UserGroup ug where cast(ug.createdOn as date) between :fromDate and :toDate")
  List<UserGroup> findAllFiltered(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

  @Override
  Optional<UserGroup> findById(@Param("id") Long id);

  @Query(
      nativeQuery = true,
      value =
          "SELECT UR.RIGHTID,RM.RIGHTDISPLAYNAME as rightName,  "
              + "       (CASE UR.ALLOWVIEW WHEN 1 THEN 'true' else 'false' end) AS allowView, "
              + "       (CASE UR.ALLOWADD WHEN 1 THEN 'true' else 'false' end) AS allowAdd, "
              + "       (CASE UR.ALLOWEDIT WHEN 1 THEN 'true' else 'false' end) AS allowEdit, "
              + "       (CASE UR.ALLOWDELETE WHEN 1 THEN 'true' else 'false' end) AS allowDelete "
              + "        FROM USERGROUPSMASTER GR "
              + "        INNER JOIN USERASSIGNEDRIGHTS UR ON UR.GROUPID = GR.ID "
              + "        RIGHT JOIN  RIGHTSMASTER RM  ON RM.ID=UR.RIGHTID "
              + "        WHERE GR.ID=?1 "
              + "        UNION ALL "
              + "        SELECT RM.ID,RM.RIGHTDISPLAYNAME as rightName,'false' as allowView,  "
              + "        'false' as allowAdd, 'false' as allowEdit,'false' as allowDelete "
              + "          FROM USERGROUPSMASTER GM ,RIGHTSMASTER RM "
              + "          WHERE GM.ID=?2 AND RM.ID NOT IN (SELECT ASR.RIGHTID  FROM USERASSIGNEDRIGHTS ASR WHERE ASR.GROUPID = ?3)")
  List<UserGroupRightsAccess> getUserGroups(Long id, Long id2, Long id3);

  @Query(
      nativeQuery = true,
      value =
          "SELECT ID as rightId, RIGHTDISPLAYNAME as rightName,"
              + " 'false' as allowView, 'false' as allowAdd, 'false' as allowEdit,"
              + "  'false' as allowDelete FROM RIGHTSMASTER")
  List<UserGroupRightsAccess> getUserGroupRights();

  @Query("SELECT u FROM UserGroup u where u.groupCode=:groupCode")
  UserGroup findByGroupCode(@Param("groupCode") String groupCode);

  //	@Query(nativeQuery = true, value = "SELECT AllowAdd, AllowDelete, AllowEdit, AllowView,rightId,
  // GroupId FROM userassignedrights")
  @Query(
      nativeQuery = true,
      value =
          "SELECT case when r.AllowAdd='1' then 'yes' else 'Not Allowed' end as allowAdd, case when r.AllowDelete='0' then 'No' else 'Allowed' end as allowDelete, case when r.AllowEdit='1' then 'yes' else 'Not Allowed' end as allowEdit, case when r.AllowView='1' then 'yes' else 'Not Allowed' end as allowView, r.rightId as rightId, r.GroupId as groupId, g.GroupName as groupName, rm.RightDisplayName as rightName FROM userassignedrights r join rightsmaster rm on r.rightId = rm.id join usergroupsmaster g on r.GroupId = g.id")
  List<UserGroupRights> getuserRoles();
}
