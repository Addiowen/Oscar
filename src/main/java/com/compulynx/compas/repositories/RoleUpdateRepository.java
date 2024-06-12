package com.compulynx.compas.repositories;

import com.compulynx.compas.models.RoleUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface RoleUpdateRepository extends JpaRepository<RoleUpdate, Integer> {

    @Query(value = "ALTER TABLE roleupdate SET status =:status, updated_by =:updatedBy, updated_at =:updatedAt WHERE id =:id", nativeQuery = true)
    void updateById(@Param("id") int id, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedAt") Date updatedAt);


//    @Query(value = "select r.Id,\n" +
//            "       u.GroupCode,\n" +
//            "       u.GroupName,\n" +
//            "       u.GroupTypeID,\n" +
//            "       r.status,\n" +
//            "       r.created_by,\n" +
//            "       r.updated_by,\n" +
//            "       r.created_at,\n" +
//            "       r.updated_at,\n" +
//            "       r.update_json,\n" +
//            "       r.group_id,\n" +
//            "       u.id\n" +
//            "from roleupdate r\n" +
//            "         join usergroupsmaster u\n" +
//            "              on r.group_id = u.id", nativeQuery = true)
//    List<RoleUpdate> findAllModifiedRoles();


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update roleupdate set update_json=?1 where id=?1")
    int addUpdateJSONtoRoleUpdate(
            String updateJSON
    );

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update roleupdate set status='A', updated_at=getDate(), updated_by=?2 where id=?1")
    void approveById(
            Long id,
            String principal
    );


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update roleupdate set status='R', updated_at=getDate(), updated_by=?2 where id=?1")
    void rejectById(Long id, String principal);
}
