package com.compulynx.compas.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.compulynx.compas.config.DbQueries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.CardPrint;
import com.compulynx.compas.models.User;
import com.compulynx.compas.models.extras.UsernamesExists;
import com.compulynx.compas.models.extras.UsersToActivate;
import com.compulynx.compas.models.extras.UsersToDeactivate;
import com.compulynx.compas.models.extras.UsersToVerify;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  // @Query("select u from User u where u.username=?1 and u.password=?2 and
  // u.status=1 and approved='V' ")
  User findByUsernameAndPassword(String username, String password);
  
  User findByEmail(String email);
  
  User findByUsername(String username);

  @Query(nativeQuery = true, value = "select * from users where verified = 'V'")
  List<User> findAllApproved();

  @Query("select count(u) from User u")
  int getUserCount();

  @Query(
      nativeQuery = true,
      value =
          "SELECT ROW_NUMBER() OVER(ORDER BY u.id) as counter,  "
              + " u.id, u.created_by as createdBy,b.BranchName as branchName, u.middle_name as middleName,u.first_name as firstName,u.last_name as lastName,u.phone,u.email,u.updated_at as updatedAt, u.updated_user_json as updateJson, (select fullname from users a  where u.updated_by=a.id) as updatedByName, (select username from users a  where u.updated_by=a.id) as updatedBy ,(select fullname from users a  where u.created_by=a.id) as createdByName, u.fullname ,um.GroupName, u.created_at as createdAt "
              + " FROM users u "
              + " inner join usergroupsmaster um on um.id=u.group_id  "
              + " inner join BranchMaster b on b.branchCode=u.branchId "
              + " where u.verified<>'V'")
  List<UsersToVerify> getUsersToVerify();

  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value =
          "UPDATE users set verified=?3, verified_on=getDate(), verified_by=?2, status=?4 WHERE id=?1")
  int approveUsers(Long userId, Long approver, String veriStatus, boolean activeStatus);

  @Query(nativeQuery = true, value = "SELECT u.username FROM users u")
  List<UsernamesExists> getExistingUsernames();

  @Query(nativeQuery = true, value = "SELECT *FROM users WHERE id=?1")
  User findById(String id);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "UPDATE users set logged_in=1 WHERE id=?1")
  int SetUserLoggedin(Long userId);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "UPDATE users set deleted =?2 WHERE id =?1")
  int toggleUserDelete(Long userId, boolean deleteValue);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "UPDATE users set logged_in = 0 WHERE username=?1")
  int SetUserLoggedOut(String username);

  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value =
          "UPDATE users set verified='V',fullname=?1,first_name=?2,last_name=?3,middle_name=?4,email=?5,phone=?6,group_id=?7,branchId=?8, status=?10, verified_by=?11, update_status='V' WHERE id=?9")
  int updateUsers(
      String fullname,
      String firstname,
      String lastname,
      String middlename,
      String email,
      String phone,
      int group_id,
      String branch,
      Long userId,
      Boolean status,
      int approver);

  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value =
          "UPDATE users set verified='V',status='1', verified_by=?2, update_status='R' WHERE id=?1")
  int rejectUpdate(Long userId, int approver);

  @Modifying
  @Transactional
  @Query(
      value =
          "UPDATE users set password = :password, password_last_update = :password_last_update, first_loggin = 0 WHERE email = :username",
      nativeQuery = true)
  int updatePassword(
      @Param("username") String username,
      @Param("password") String password,
      @Param("password_last_update") Date passwordLastUpdate);

  // get users to deactivate
  @Query(
      nativeQuery = true,
      value =
          "SELECT ROW_NUMBER() OVER(ORDER BY u.id) as counter,  "
              + " u.id,(select fullname from users a  where u.created_by=a.id) as createdBy, u.fullname, um.GroupName,convert(date,u.created_at) as createdAt "
              + " FROM users u "
              + " inner join usergroupsmaster um on um.id=u.group_id "
              + " where u.status=1")
  List<UsersToDeactivate> getUsersToDeactivate();

  @Modifying
  @Transactional
  @Query(value = "UPDATE users set status= 0  WHERE username = :userId", nativeQuery = true)
  int deactivateUser(@Param("userId") Long userId);
  //
  //	@Modifying
  //	@Transactional
  //	@Query(value = "UPDATE users set status = 1 WHERE username =?1", nativeQuery = true)
  //	int activateUser(String username);

  @Query(nativeQuery = true, value = "SELECT *FROM users WHERE id=?1")
  User findByUserId(Long userId);

  @Query(
      nativeQuery = true,
      value =
          "SELECT ROW_NUMBER() OVER(ORDER BY u.id) as counter,  "
              + " u.id,(select fullname from users a  where u.created_by=a.id) as createdBy, u.fullname ,um.GroupName,convert(date,u.created_at) as createdAt "
              + " FROM users u "
              + " inner join usergroupsmaster um on um.id=u.group_id "
              + " where u.status=0")
  List<UsersToActivate> getUsersToActivate();

  @Modifying
  @Transactional
  @Query(value = "UPDATE users set status= 1  WHERE username = :userId", nativeQuery = true)
  int activateUser(@Param("userId") Long userId);

  // @Query(nativeQuery = true, value = "SELECT (select fullname from users a
  // where u.created_by=a.id) as createdBy, u.fullname ,um.GroupName FROM users u
  // inner join usergroupsmaster um on um.id=u.group_id where u.created_at BETWEEN
  // ?1 AND ?2")
  @Query(
      nativeQuery = true,
      value = "SELECT * FROM users u WHERE cast(u.created_at as date) BETWEEN ?1 AND ?2")
  List<User> getAll(String fromDate, String toDate);


  @Query(nativeQuery = true, value = DbQueries.FIND_APPROVER_BY_BRANCH)
  List<User> findApproverByBranch(
      @Param("approvalType") String approvalType, @Param("branch") String branch);

  @Modifying
  @Transactional
  @Query(value = "UPDATE users set usedPasswords = :usedPasswords WHERE email = :username", nativeQuery = true)
  int saveCurrentPasswordInList(
          @Param("usedPasswords") String usedPasswords,
          @Param("username") String username
          );


}
