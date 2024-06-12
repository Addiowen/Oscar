package com.compulynx.compas.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.compulynx.compas.Requests.UserGroupReq;
import com.compulynx.compas.models.User;
import com.compulynx.compas.models.UserAssignedRights;

import com.compulynx.compas.models.extras.UserGroupRightsAccess;
import com.compulynx.compas.repositories.UserAssignedRightsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.UserGroup;
import com.compulynx.compas.models.extras.UserGroupRights;
import com.compulynx.compas.repositories.UseGroupRepositories;

@Service
public class UserGroupService {

  @Autowired private UseGroupRepositories groupRepositories;

  @Autowired
  UserAssignedRightsRepository userAssignedRightsRepository;



  @Autowired private UserService userService;

  public List<UserGroup> userGroups() {
    System.out.println("Got here");
    return groupRepositories.findAll();
  }



  public List<UserGroup> userGroupsFiltered(String fromdate, String todate) throws ParseException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date fromDate = format.parse(fromdate);
    Date toDate = format.parse(todate);
    return groupRepositories.findAllFiltered(fromDate, toDate);
  }

  public Optional<UserGroup> getUserGroup(Long id) {
    // TODO Auto-generated method stub
    return groupRepositories.findById(id);
  }

  public UserGroup addGroup(UserGroupReq userGroupReq, String principal) {
    System.out.println("****** User Group REQ ************");
    System.out.println(userGroupReq);
    System.out.println("****** User Group REQ ************");
    User createdBy = userService.findByUsername(principal);
    User groupCreatedBy = userService.findById(userGroupReq.getCreatedBy().toString());
    UserGroup userGroup =
        new UserGroup(
            userGroupReq.getGroupCode(),
            userGroupReq.getGroupName(),
            0,
            userGroupReq.getActive(),
            groupCreatedBy,
            userGroupReq.getRights(),
            null,
            new Date(),
            null);
    userGroup.setId(userGroupReq.getId());
    System.out.println("*********** User Group ******");
    System.out.println(userGroup);
    if (userGroup.getId() == 0) {
      UserGroup g = groupRepositories.findByGroupCode(userGroup.getGroupCode());
      if (g == null) {
        return groupRepositories.save(userGroup);
      } else {
        return null;
      }
    }
    userGroup.setModifiedOn(new Date());
    userGroup.setModifiedBy(createdBy);
    return groupRepositories.save(userGroup);
  }
  //	public UserGroup findByGroupCode(String groupCode) {
  //		return groupRepositories.findByGroupCode(groupCode);
  //	}

  public List<UserGroupRightsAccess> getGroupRights(Long id, Long id2, Long id3) {
    // TODO Auto-generated method stub
    return groupRepositories.getUserGroups(id, id2, id3);
  }

  public List<UserGroupRightsAccess> getUserGroupRights() {
    System.out.println("In User Groups Rights..");
    // TODO Auto-generated method stub
    return groupRepositories.getUserGroupRights();
  }

  public List<UserGroupRights> getuserRoles() {
    // TODO Auto-generated method stub
    return groupRepositories.getuserRoles();
  }

  // added to add/ edit userAssignedRights Table
  // check for userAssignedRight given rightId and groupId
  public Optional<UserAssignedRights> getUserAssignedRight(Integer rightId, Integer groupId) {
    return userAssignedRightsRepository.recordsByRightIdAndGroupId(rightId, groupId);
  }

  // added new record to userAssignedRights Table
  // check for userAssignedRight given rightId and groupId
  //db insert...make sure that every column that does not have defaults and that expects to a have a value should be field either with actual data or false if null
  public Integer addUserAssignedRight(UserAssignedRights r, Integer group_Id) {

    Boolean allowAdd = r.isAllowAdd() == null ? false: r.isAllowAdd();
    Boolean allowView = r.isAllowView()  == null ? false: r.isAllowView();
    Boolean allowEdit = r.isAllowEdit() == null ? false: r.isAllowEdit();
    Boolean allowDelete = r.isAllowDelete() == null ? false: r.isAllowDelete();

    Integer createdBy  = r.getCreatedBy();
    Integer rightId = r.getRightId();
    String rightName = r.getRightName();
//    UserGroup groupId = r.getGroupId();
    System.out.println("=======================================");
    System.out.println(allowAdd +" " + allowView +" " + allowEdit +" " + allowDelete +" " + createdBy +" " + rightId +" " + rightName +" " + group_Id);
    return userAssignedRightsRepository.addUserAssignedRight(allowAdd, allowView, allowEdit, allowDelete, createdBy, rightId, rightName, group_Id);
  }

  public Integer editUserAssignedRight(UserAssignedRights r, Integer group_Id) {

    Boolean allowAdd = r.isAllowAdd();
    Boolean allowView = r.isAllowView();
    Boolean allowEdit = r.isAllowEdit();
    Boolean allowDelete = r.isAllowDelete();

    Integer createdBy  = r.getCreatedBy();
    Integer rightId = r.getRightId();
    String rightName = r.getRightName();

    return userAssignedRightsRepository.updateUserAssignedRight(allowAdd, allowView, allowEdit, allowDelete, createdBy, rightId, rightName, group_Id);
  }
}
