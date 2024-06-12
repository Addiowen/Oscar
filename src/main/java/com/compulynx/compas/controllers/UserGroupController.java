package com.compulynx.compas.controllers;

import com.compulynx.compas.Requests.UserGroupReq;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.models.*;
import com.compulynx.compas.models.extras.GlobalResponse;
import com.compulynx.compas.models.extras.UserGroupImpl;
import com.compulynx.compas.models.extras.UserGroupRights;
import com.compulynx.compas.models.extras.UserGroupRightsAccess;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.RoleUpdateService;
import com.compulynx.compas.services.UserGroupService;
import com.compulynx.compas.services.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author KENSON
 *
 */
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class UserGroupController {

    private final String module = "User Group";
    private final Gson gson = new Gson();
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private RoleUpdateService roleUpdateService;
    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private UserService userService;

    private AuditTrail auditTrail;

    /**
     * user groups
     *
     * @return
     */
    @GetMapping("/usergroups")
    public ResponseEntity<?> getUserGroups() {
        List<UserGroup> userGroups = userGroupService.userGroups();

        if (userGroups.isEmpty()) {
            return new ResponseEntity<>(
                    new CustomResponse(
                            CustomResponse.APIV, 404, false, "cannot find usergroups", new HashSet<>(userGroups)),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, "usergroups", new HashSet<>(userGroups)),
                HttpStatus.OK);
    }


    // New Mapping for populating Role Update report
    // @mike Fixing details on the roleUpdate table
    @GetMapping("/getRoleUpdates")
    public ResponseEntity<?> getRoleUpdates() {

        List<RoleUpdate> roleUpdates = roleUpdateService.fetchAll();
        List<UserGroup> userGroups = userGroupService.userGroups();

        List<RoleBasedReportDto> roleUpdatesNew = new ArrayList<>();

        for (RoleUpdate role : roleUpdates) {

            RoleBasedReportDto roleBasedReportDto = new RoleBasedReportDto();

            String updateJson = role.getUpdateJSON();


            Type userListType = new TypeToken<ArrayList<UserAssignedRights>>() {}.getType();

            ArrayList<UserAssignedRights> rightsArrayList = gson.fromJson(updateJson, userListType);

            List<Role> allRoles = new ArrayList<>();

            rightsArrayList.forEach(i -> {
                Role myRole = new Role();
                myRole.setTitle(i.getRightName());

                allRoles.add(myRole);
            });


            UserGroup selectedGroup = null;

            for (UserGroup group: userGroups
            ) {
                if (group.getId().toString().equals(role.getGroupId())) {
                    selectedGroup = group;
                    System.out.println(group.getId()+ " : " + role.getGroupId());
                    System.out.println(group.getId().toString().equals(role.getGroupId()));
                }
            }

            roleBasedReportDto.setId(role.getId());
            roleBasedReportDto.setGroupCode(selectedGroup.getGroupCode());
            roleBasedReportDto.setGroupName(selectedGroup.getGroupName());
            roleBasedReportDto.setActive(role.getStatus() == "A");

            roleBasedReportDto.setCreatedBy(userService.findByUsername(role.getCreatedBy()));
            roleBasedReportDto.setCreatedOn(role.getCreatedAt());
            roleBasedReportDto.setModifiedBy(userService.findByUsername(role.getUpdatedBy()));
            roleBasedReportDto.setModifiedOn(role.getUpdatedAt());

            Role[] roles = new Role[allRoles.size()];

            for (int i = 0; i < allRoles.size(); i++) {
                roles[i] = allRoles.get(i);
            }

            roleBasedReportDto.setRoles(roles);

            roleUpdatesNew.add(roleBasedReportDto);

            System.out.println("roleUpdatesNew::OLD");
            System.out.println(roleUpdatesNew);

//            Collections.sort(roleUpdatesNew, new DateComparator());

            System.out.println("roleUpdatesNew::NEW");
            System.out.println(roleUpdatesNew);
        }




            if (roleUpdates.isEmpty()) {
                return new ResponseEntity<>(
                        new CustomResponse(
                                CustomResponse.APIV, 404, false, "cannot find any roles", new HashSet<>(roleUpdatesNew)),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(
                    new CustomResponse(CustomResponse.APIV, 200, true, "usergroups", new HashSet<>(roleUpdatesNew)),
                    HttpStatus.OK);

    }


    @GetMapping("/usergroupsFiltered")
    public ResponseEntity<?> getUserGroupsFiltered(
            @RequestParam String fromDate, @RequestParam String toDate) {
        try {
            List<UserGroup> userGroups = userGroupService.userGroupsFiltered(fromDate, toDate);
            if (userGroups.isEmpty()) {
                return new ResponseEntity<>(
                        new CustomResponse(
                                CustomResponse.APIV,
                                404,
                                false,
                                "cannot find usergroups",
                                new HashSet<>(userGroups)),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(
                    new CustomResponse(
                            CustomResponse.APIV, 200, true, "usergroups", new HashSet<>(userGroups)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new CustomResponse(
                            CustomResponse.APIV,
                            404,
                            false,
                            "Error fetching UserGroups " + e.getMessage(),
                            new HashSet<>(null)),
                    HttpStatus.NOT_FOUND);
        }
    }

    /**
     * user group
     *
     * @param id
     * @return
     */
    @GetMapping("/usergroups/usergroup")
    public ResponseEntity<?> userGroup(@RequestParam(value = "id") Long id) {

        Optional<UserGroup> group = userGroupService.getUserGroup(id);

        if (group == null) {
            return new ResponseEntity<>(
                    new CustomResponse(CustomResponse.APIV, 404, false, "group id" + id + "does not exist"),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, "group", group), HttpStatus.OK);
    }


    // Controller to send records to frontEnd
    // This populates the list kwa Edited-roles-auth page
    @GetMapping("/usergroups/getrightsupdate")
    public ResponseEntity<?> getAllRightsEdit() {

        List<RoleUpdateDto> roleUpdateDtoList = new ArrayList<>();

        List<RoleUpdate> allRoles = roleUpdateService.fetchAll();

        List<RoleUpdate> pendingRoles = new ArrayList<>();

        allRoles.stream()
                .forEach(roleUpdate -> {
                    if (roleUpdate.getStatus().equals("P")) {
                        pendingRoles.add(roleUpdate);
                    }
                });

        List<UserGroup> allGroups = userGroupService.userGroups();


        if (pendingRoles == null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new GenericResponse(true, null));
        }

        for (RoleUpdate role : pendingRoles) {

            String id = role.getGroupId();

            String rightGroupName = null;
            String rightGroupCode = null;


            for (UserGroup group : allGroups) {
                System.out.println("Trying to match id");
                if (group.getId() == Integer.parseInt(id)) {
                    System.out.println("Found Matching Id");
                    rightGroupName = group.getGroupName();
                    rightGroupCode = group.getGroupCode();

                    break;
                }
            }


            RoleUpdateDto roleUpdateDto = new RoleUpdateDto();

            roleUpdateDto.setId(role.getId());
            roleUpdateDto.setGroupId(role.getGroupId());
            roleUpdateDto.setGroupName(rightGroupName);
            roleUpdateDto.setGroupCode(rightGroupCode);
            roleUpdateDto.setRights(role.getUpdateJSON());
            roleUpdateDto.setCreatedBy(role.getCreatedBy());
            roleUpdateDto.setCreatedAt(role.getCreatedAt());

            roleUpdateDtoList.add(roleUpdateDto);
        }


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(true, roleUpdateDtoList));
    }


    // For Adding New Group
    @PostMapping("/usergroups/assignrights")
    public ResponseEntity<?> addUserGroup(
            @Validated @RequestBody UserGroupReq userGroupReq,
            @RequestHeader("principal") String principal,
            HttpServletRequest rawRequest) {

        String add = "Add User Group(" + userGroupReq.getGroupName() + ")";
        String edit = "Edit User Group(" + userGroupReq.getGroupName() + ")";
        String action = userGroupReq.getId() == 0 ? add : edit;
        UserGroup userGroup = userGroupService.addGroup(userGroupReq, principal);
        String actionStatus = "Success";
        ResponseEntity responseEntity = null;
        String failReason = null;
        if (userGroup == null) {
            actionStatus = "Failed";
            failReason = "Duplicate user group detected";
            responseEntity =
                    new ResponseEntity<>(
                            new CustomResponse(
                                    CustomResponse.APIV,
                                    203,
                                    false,
                                    "failed to save user groups, user group code already exists"),
                            HttpStatus.OK);
        } else {
            responseEntity =
                    new ResponseEntity<>(
                            new CustomResponse(CustomResponse.APIV, 201, true, "data added successfully"),
                            HttpStatus.OK);
        }
        auditTrailService.saveAuditTrail(
                principal, rawRequest, module, action, null, null, actionStatus, failReason);
        return responseEntity;
    }

    // This is the updated method for adding maker checker functionality to edit rights
    // Iko kwa the modal that presents itself when you click on authorize kwa Edited-roles-auth page
    // It Updates the roleupdate table with the updated role details
    @PostMapping("/usergroups/editrights")
    public ResponseEntity<?> editUserGroups(
            @Validated @RequestBody UserGroupReq userGroupReq,
            @RequestHeader("principal") String principal,
            HttpServletRequest rawRequest
    ) {
        System.out.println("Here is the userGroupReq");
        System.out.println(userGroupReq);

        RoleUpdate roleUpdate = new RoleUpdate();

        roleUpdate.setGroupId(String.valueOf(userGroupReq.getId()));
        roleUpdate.setStatus("P");
        roleUpdate.setCreatedAt(new Date());


        System.out.println("Here is the change: gson.toJson(userGroupReq.getRights())");
        System.out.println(gson.toJson(userGroupReq.getRights()));

        List<UserAssignedRights> rights = userGroupReq.getRights();

        rights.forEach(right -> {
            right.setCreatedBy(userGroupReq.getCreatedBy().intValue());
        });

        roleUpdate.setUpdateJSON(gson.toJson(rights));
        roleUpdate.setCreatedBy(principal);

        String action = "Edit User Group (" + userGroupReq.getGroupName() + ")";
        roleUpdateService.addRoleRecord(roleUpdate);

        String actionStatus = "Success";
        String failReason = null;

        auditTrailService.saveAuditTrail(
                principal, rawRequest, module, action, null, null, actionStatus, failReason);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "data added successfully"));

    }



    // This is the updated method for approving edited rights
    // It adds a new role in userassignedrights table
    // It sets the status in the roleupdate table to A(Approved)
    // It adds updated at and updated by
    @PostMapping("/usergroups/approverights")
    public ResponseEntity<?> approveUserGroup(
            @Validated @RequestBody UserGroupReq userGroupReq,
            @RequestHeader("principal") String principal,
            HttpServletRequest rawRequest
    ) {


        // check if record exists in userAssigned rights
        Optional<RoleUpdate> roleUpdateOptional = roleUpdateService.fetchById(userGroupReq.getId());
        RoleUpdate roleUpdate = roleUpdateOptional.get();

        if (roleUpdate == null) {

        } else {
            Integer group_Id = Integer.parseInt(roleUpdate.getGroupId());

            Integer currentRightId;
            List<UserAssignedRights> myRights = userGroupReq.getRights();

            for (UserAssignedRights r : myRights) {
                currentRightId = r.getRightId();

                System.out.println(currentRightId + "right-group" + group_Id);

                Optional<UserAssignedRights> userAssignedRights;

                userAssignedRights = userGroupService.getUserAssignedRight(currentRightId, group_Id);
                System.out.println("Added Item to Optional UserAssignedRight");


                System.out.println("userAssignedRights");

                if (userAssignedRights.isPresent()) {

                    System.out.println(r.isAllowAdd());
                    System.out.println(r.isAllowEdit());
                    System.out.println(r.isAllowView());
                    System.out.println(r.isAllowDelete());
                    System.out.println(group_Id);

                    userGroupService.editUserAssignedRight(r, group_Id);

                } else {

                    // if record does not exist, create record
                    System.out.println("Value is null");
                    userGroupService.addUserAssignedRight(r, group_Id);
                }

            }
            // alter role update... set status to approved, time == currentTime, Approver == principal
            roleUpdateService.approveRoleChange(userGroupReq.getId(), principal);

            String action = "Approve RoleChange (" + userGroupReq.getGroupName() + ")";
            roleUpdateService.addRoleRecord(roleUpdate);

            String actionStatus = "Success";
            String failReason = null;


            auditTrailService.saveAuditTrail(
                    principal, rawRequest, module, action, null, null, actionStatus, failReason);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new GenericResponse(true, "user approved successfully."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse(true, "User not created successfully."));
    }

    // This is the updated method for rejecting edited rights
    // It sets the status in the roleupdate table to A(Approved)
    @PostMapping("/usergroups/rejectrights")
    public ResponseEntity<?> rejectUserGroup(
            @Validated @RequestBody UserGroupReq userGroupReq,
            @RequestHeader("principal") String principal,
            HttpServletRequest rawRequest
    ) {
        System.out.println(userGroupReq);

        // alter role update... set status to rejected, time == currentTime, Approver == principal
        roleUpdateService.rejectRoleChange(userGroupReq.getId(), principal);

        String action = "Reject RoleChange (" + userGroupReq.getGroupName() + ")";

        String actionStatus = "Success";
        String failReason = null;


        auditTrailService.saveAuditTrail(
                principal, rawRequest, module, action, null, null, actionStatus, failReason);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(true, "user rejected successfully."));
    }


    @GetMapping("/usergroups/gtUserGroups")
    public ResponseEntity<?> getUserGroup() {
        try {
            List<UserGroup> groups = userGroupService.userGroups();
            List<UserGroupImpl> rts = new ArrayList<UserGroupImpl>();
            if (groups.isEmpty()) {
                return new ResponseEntity<>(
                        new GlobalResponse(
                                GlobalResponse.APIV, "404", false, "cannot find usergroups", new HashSet<>(rts)),
                        HttpStatus.NOT_FOUND);
            }
            for (UserGroup group : groups) {
                UserGroupImpl obj = new UserGroupImpl();
                obj.setId(group.getId());
                obj.setActive(group.isActive());
                obj.setCreatedBy(
                        group.getCreatedBy() == null ? 0 : group.getCreatedBy().getId().intValue());
                obj.setGroupCode(group.getGroupCode());
                obj.setGroupName(group.getGroupName());
                List<UserGroupRightsAccess> rights =
                        userGroupService.getGroupRights(group.getId(), group.getId(), group.getId());
                obj.setRights(rights);
                rts.add(obj);
            }
            return new ResponseEntity<>(
                    new GlobalResponse(GlobalResponse.APIV, "000", true, "usergroups", new HashSet<>(rts)),
                    HttpStatus.OK);
        } catch (Exception e) {
            GlobalResponse resp =
                    new GlobalResponse("404", "error processing request", false, GlobalResponse.APIV);
            e.printStackTrace();
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @GetMapping("/usergroups/gtRights")
    public ResponseEntity<?> getUserGroupRights() {
        try {
            List<UserGroupRightsAccess> userGroups = userGroupService.getUserGroupRights();

            if (userGroups.isEmpty()) {
                return new ResponseEntity<>(
                        new GlobalResponse(
                                GlobalResponse.APIV,
                                "404",
                                false,
                                "cannot find usergroups",
                                new HashSet<>(userGroups)),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(
                    new GlobalResponse(
                            GlobalResponse.APIV, "000", true, "usergroups", new HashSet<>(userGroups)),
                    HttpStatus.OK);
        } catch (Exception e) {
            GlobalResponse resp =
                    new GlobalResponse("404", "error processing request", false, GlobalResponse.APIV);
            e.printStackTrace();
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    // user roles report
    @GetMapping(value = "/roles/report")
    public ResponseEntity<?> getRoleReport() {
        try {
            List<UserGroupRights> userGroups = userGroupService.getuserRoles();

            System.out.println("User Roles: " + userGroups);
            if (userGroups.size() <= 0) {
                System.out.println("No Data");
                return new ResponseEntity<>(userGroups, HttpStatus.OK);
            }

            System.out.println("Has User Data: here it is:");

            return new ResponseEntity<>(userGroups, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new CustomResponse(CustomResponse.APIV, 200, false, "Server error processing request"),
                    HttpStatus.OK);
        }
    }
}

class DateComparator implements Comparator<RoleBasedReportDto> {
    public int compare(RoleBasedReportDto o1, RoleBasedReportDto o2) {
        RoleBasedReportDto r1 = (RoleBasedReportDto) o1;
        RoleBasedReportDto r2 = (RoleBasedReportDto) o2;

        return r1.getModifiedOn().compareTo(r2.getModifiedOn());
    }
}
