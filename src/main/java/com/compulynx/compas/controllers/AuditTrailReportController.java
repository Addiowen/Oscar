package com.compulynx.compas.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.compulynx.compas.models.Branch;
import com.compulynx.compas.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.compulynx.compas.Responses.AuditTraiDTO;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.models.AuditTrail;
import com.compulynx.compas.models.User;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class AuditTrailReportController {
  private AuditTrailService auditTrailService;
  private UserService userService;
  private BranchService branchService;
  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  Date date = new Date();
  private String module = "Audit Report";

  @Autowired
  public AuditTrailReportController(
      AuditTrailService auditTrailService, UserService userService, BranchService branchService) {
    this.auditTrailService = auditTrailService;
    this.userService = userService;
    this.branchService = branchService;
  }

  @GetMapping(value = "/audit/report")
  public ResponseEntity<?> getAuditReport(
      @RequestParam(value = "fromDate") String fromDate,
      @RequestParam(value = "toDate") String toDate,
      @RequestParam(value = "branch") String branchProp,
      @RequestParam(value = "user") String userProp,
      HttpServletRequest rawRequest,
      @RequestHeader("principal") String principal) {
    String actionStatus = "";
    String failReason = null;
    
    ResponseEntity responseEntity = null;
    
    try {
    	System.out.println(fromDate+ " "+ toDate+ " "+branchProp+ " "+userProp );
      List<AuditTrail> audittrail = auditTrailService.getAuditTrail(fromDate, toDate, branchProp, userProp);
      List<User> users = userService.getUsers();
      List<AuditTraiDTO> auditTrailDto = new ArrayList<>();
      List<Branch> branches = branchService.branches();

      if (!audittrail.isEmpty()) {
        audittrail.forEach(
            trail -> {
              AuditTraiDTO dto =
                  new AuditTraiDTO(
                      trail.getUserId(),
                      trail.getModule(),
                      trail.getDate(),
                      trail.getAction(),
                      trail.getRemoteIP(),
                      trail.getActionStatus(),
                      trail.getBranch(),
                      trail.getBranchName(),
                      trail.getFailReason()
                      );
              users.forEach(
                  user -> {
                	  System.out.println("inside loop users list !!");
                    if (user.getId() == trail.getUserId()) {
                      dto.setUsername(user.getUsername());
                    }
                  });
              branches.forEach(
                  branch -> {
                	  System.out.println("inside loop branches list !!");
                    if (branch.getBranchCode() == trail.getBranch()) {
                    	System.out.println("branch_code from branch_master :"+branch.getBranchCode() + "branch_code from audit_rail :" +trail.getBranch());
                      dto.setBranchName(branch.getBranchName());
                    }
                  });
              auditTrailDto.add(dto);
            });
      }
      if (audittrail.size() <= 0) {
        System.out.println("No Data");
        return new ResponseEntity<>(audittrail, HttpStatus.OK);
      }

      System.out.println("Has Data");
      responseEntity = new ResponseEntity<>(auditTrailDto, HttpStatus.OK);
      actionStatus = "Success";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV, 200, false, "Server error processing request"),
              HttpStatus.OK);
      failReason = e.getMessage();
      actionStatus = "Failed";
    }

    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        "Fetch audit trail reports",
        null,
        null,
        actionStatus,
        failReason);
    return responseEntity;
  }
}
