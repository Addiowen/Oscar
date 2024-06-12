package com.compulynx.compas.controllers;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.handlers.UserResponse;
import com.compulynx.compas.models.PrinterType;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.PrinterTypeService;

/**
 * @author KENSON
 *
 */
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class PrinterTypeController {
	@Autowired
	private PrinterTypeService printerTypeService;
	@Autowired
	  private AuditTrailService auditTrailService;
	  private final String module = "Printer Type";
	 @PostMapping(value = "/printerType/create")
	  public ResponseEntity<?> addPrinterType(
	      @RequestBody PrinterType printerType,
	      @RequestHeader("principal") String principal,
	      HttpServletRequest rawRequest) {
	    
	    ResponseEntity responseEntity = null;
	    String actionStatus = "";
	    String failReason =null;
	    String action ="create printer type";
	    PrinterType	printertype =printerTypeService.findByName(printerType.getPrinterName());
	    try {
	    	if(printertype !=null && printerType.getId()==0) {
	    	      actionStatus = "Failed";
		  	        failReason = "Duplicate printer name creation detected";
		  	        responseEntity =
		  	            new ResponseEntity<>(
		  	                new UserResponse(UserResponse.APIV, 203, false, "printer name already exists!"),
		  	                HttpStatus.OK);
	    	}
	    	else {
	 printerType = printerTypeService.addPrinterType(printerType);  	
	    if (printerType == null) {
	    	actionStatus = "failed";
	      responseEntity =
	          new ResponseEntity<>(
	              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to add printer type"),
	              HttpStatus.OK);
	    } else {
	   
	    	actionStatus = "success";
	      responseEntity =
	          new ResponseEntity<>(
	              new CustomResponse(CustomResponse.APIV, 201, true, "printer type updated successfully"),
	              HttpStatus.OK);
	 
	       }
	      } 
	    }catch (Exception e) {
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
	  
	    @GetMapping(path = "/printerTypes")
	    public ResponseEntity<?> fetchAll(
	    	    @RequestHeader("principal") String principal,
			      HttpServletRequest rawRequest
			      ){
	    	 List<PrinterType> printerTypes = printerTypeService.getPrinterTypes();
	    	    ResponseEntity responseEntity = null;
	    	    String actionStatus = "";
	    	    String failReason =null;
	    	    String action ="create printer type";
	    	if (printerTypes.isEmpty()) {
	    		actionStatus="No printer Types found";
	    	      responseEntity =
	    	          new ResponseEntity<>(
	    	              new CustomResponse(
	    	                  CustomResponse.APIV, 404, false, "No printer Types found", new HashSet<>(printerTypes)),
	    	              HttpStatus.NOT_FOUND);
	    	    } else {
	    	    	actionStatus="Card printer found";
	    	      responseEntity =
	    	          new ResponseEntity<>(
	    	              new CustomResponse(
	    	                  CustomResponse.APIV, 200, true, "card printer found", new HashSet<>(printerTypes)),
	    	              HttpStatus.OK);
	    	    }
	    	   auditTrailService.saveAuditTrail(
	   	            principal, rawRequest, module, action, null, null, actionStatus, failReason);
	    	    return responseEntity;
	    }
	    
		 @PostMapping(value = "/printerType/update")
		  public ResponseEntity<?> updatePrinterType(
		      @RequestBody PrinterType printerType,
		      @RequestHeader("principal") String principal,
		      HttpServletRequest rawRequest) {
		    printerType = printerTypeService.updPrinterType(printerType);
		    ResponseEntity responseEntity = null;
		    String actionStatus = "";
		    String failReason =null;
		    String action ="update printer type";
		    if (printerType == null) {
	    		  actionStatus = "failed";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to update printer type"),
		              HttpStatus.OK);
		    } else {
		    	actionStatus = "success";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 201, true, "printer type updated successfully"),
		              HttpStatus.OK);
		 
		    }
		    auditTrailService.saveAuditTrail(
		            principal, rawRequest, module, action, null, null, actionStatus, failReason);
		    return responseEntity;
		  }
}
