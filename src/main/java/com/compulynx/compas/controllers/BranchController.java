package com.compulynx.compas.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.handlers.UserResponse;
import com.compulynx.compas.models.AuditTrail;
import com.compulynx.compas.models.Branch;
import com.compulynx.compas.models.User;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.BranchService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KENSON
 *
 */
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class BranchController {
  @Autowired private BranchService branchService;
  public AuditTrail auditTrail;
  private final String module = "Branch";
  @Autowired
  private AuditTrailService auditTrailService;
  private Logger logger = LoggerFactory.getLogger(BranchController.class);
  // set date format
  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  Date date = new Date();

  @GetMapping(value = "/branches")
  public ResponseEntity<?> getBranches(
      HttpServletRequest rawRequest, 
      @RequestHeader("principal") String principal) {
    logger.info("---------------------[FETCH BRANCHES...]-----------------------");
    String action ="FETCH BRANCHES...";
    String actionStatus = "";
    String failReason="";
    List<Branch> branchs = branchService.branches();
    for(int i = 0; i < branchs.size(); i++) {
        System.out.println(branchs.get(i).getId());
    }

    ResponseEntity responseEntity = null;
    if (branchs.isEmpty()) {
    	actionStatus="No branches found";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV, 404, false, "No branches found", new HashSet<>(branchs)),
              HttpStatus.NOT_FOUND);
    } else {
    	actionStatus="Branches found";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV, 200, true, "branches found", new HashSet<>(branchs)),
              HttpStatus.OK);
    }
    auditTrailService.saveAuditTrail(
            principal, rawRequest, module, action, null, null, actionStatus, failReason);
    return responseEntity;
  }

  @PostMapping(value = "/branches/create")
  public ResponseEntity<?> addBranch(
      @RequestBody Branch branch,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String actionStatus = "";
    String failReason =null;
    String action ="create branch";
    
    System.out.println("branch name : "+branch.getBranchName());
    Branch brnch = branchService.findByBranchName(branch.getBranchName());
  
    ResponseEntity responseEntity = null;
    try {
    	
        if ( brnch !=null && branch.getId()==0) {
	        actionStatus = "Failed";
	        failReason = "Duplicate branch name creation detected";
	        responseEntity =
	            new ResponseEntity<>(
	                new UserResponse(
	                		UserResponse.APIV, 
	                		203,
	                		false, 
	                		"branch name already exists!"),
	                HttpStatus.OK);
	      } 
   else {
     branch = branchService.addBranch(branch);  
    if (branch == null) {
    	 actionStatus = "Failed";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to create branch"),
              HttpStatus.OK);
    } else {
    	 actionStatus = "success";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 201, true, "Record updated successfully"),
              HttpStatus.OK);
        }
      }
    } catch (Exception e) {
	      actionStatus = "Failed";
	      failReason = e.getMessage();
	      e.printStackTrace();
	      responseEntity =
	          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	              .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
    
    auditTrailService.saveAuditTrail(
            principal, rawRequest, module, action, null, null, actionStatus, failReason);
    return responseEntity;
  }
  @PostMapping(value = "/branches/update")
  public ResponseEntity<?> updateBranch(
      @RequestBody Branch branch,@RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    branch = branchService.updBranch(branch);
    ResponseEntity responseEntity = null;
    String actionStatus = "";
    String failReason =null;
    String action ="update branch";
    if (branch == null) {
    	 failReason = null;
    	  actionStatus = "Failed";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to update branch"),
              HttpStatus.OK);
    } else {
    	  actionStatus = "success";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 201, true, "Record updated successfully"),
              HttpStatus.OK);
    }
    auditTrailService.saveAuditTrail(
            principal, rawRequest, module, action, null, null, actionStatus, failReason);
    return responseEntity;
    
  }
}
