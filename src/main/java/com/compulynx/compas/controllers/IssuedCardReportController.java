package com.compulynx.compas.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.models.Branch;
import com.compulynx.compas.models.IssuedCard;
import com.compulynx.compas.models.GenericResponse;
import com.compulynx.compas.models.User;
import com.compulynx.compas.services.BranchService;
import com.compulynx.compas.services.IssuedCardService;
import com.compulynx.compas.services.UserService;


/**
 * @author KENSON
 *
 */
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1 + "/issuedCard")
public class IssuedCardReportController {

	  private IssuedCardService issuedCardService;
	  private BranchService branchService;
	  private UserService userService;

	  @Autowired
	  public IssuedCardReportController(
			  IssuedCardService issuedCardService,
	      BranchService branchService,
	      UserService userService) {
	    this.issuedCardService = issuedCardService;
	    this.branchService = branchService;
	    this.userService = userService;
	  }

	  @GetMapping("/findAll")
	  public ResponseEntity<?> findAll() {
	    try {
	      List<IssuedCard> issuedcards = (List<IssuedCard>) issuedCardService.findAll();
	      return ResponseEntity.ok(new GenericResponse(true, issuedcards));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	  }

	  @GetMapping("/findByBranchAndDate")
	  public ResponseEntity<?> findByBranchAndDate(
	      HttpServletRequest rawRequest,
	      @RequestHeader("principal") String principal,
	      @RequestParam("branchCode") String branchCode,
	      @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
	      @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
	    try {
	      User user = userService.findByUsername(principal);
	      String filterbranchCode =
		          (branchCode == null || "".equalsIgnoreCase(user.getBranchId())) ? principal : branchCode;
	      Branch branch = branchService.findBybranchCode(filterbranchCode);
	      List<IssuedCard> issuedcards =
	    		  issuedCardService.findAllByBranchAndCreatedAt(branch, fromDate, toDate);
	      return ResponseEntity.ok(new GenericResponse(true, issuedcards));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	  }

	  @GetMapping("/findByBranchAndUserAndDate")
	  public ResponseEntity<?> findByBranchAndUserAndDate(
	      HttpServletRequest rawRequest,
	      @RequestHeader("principal") String principal,
	      @RequestParam("branchCode") String branchCode,
	      @RequestParam("userName") String userName,
	      @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
	      @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
	    try {
	      String filterUserName =
	          (userName == null || "".equalsIgnoreCase(userName)) ? principal : userName;
	      User user = userService.findByUsername(filterUserName);
	      String filterbranchCode =
		          (branchCode == null || "".equalsIgnoreCase(user.getBranchId())) ? principal : branchCode;
	      Branch branch = branchService.findBybranchCode(filterbranchCode);
	      
	      List<IssuedCard> issuedcards =
	    		  issuedCardService.findAllByBranchAndUserAndCreatedAt(branch, user, fromDate, toDate);
	      return ResponseEntity.ok(new GenericResponse(true, issuedcards));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	  }

	  @GetMapping("/findByUserAndDate")
	  public ResponseEntity<?> findByUserAndDate(
	          HttpServletRequest rawRequest,
	          @RequestHeader("principal") String principal,
	          @RequestParam("userName") String userName,
	          @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
	          @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
	    try {
	      String filterUserName =
	              (userName == null || "".equalsIgnoreCase(userName)) ? principal : userName;
	      User user = userService.findByUsername(filterUserName);
	      List<IssuedCard> issuedcards =
	    		  issuedCardService.findAllByUserAndCreatedAt(user, fromDate, toDate);
	      return ResponseEntity.ok(new GenericResponse(true, issuedcards));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	              .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	  }

	  @GetMapping("/findByDate")
	  public ResponseEntity<?> findByDate(
	          HttpServletRequest rawRequest,
	          @RequestHeader("principal") String principal,
	          @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
	          @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
	    try {
	      List<IssuedCard> issuedcards =
	    		  issuedCardService.findAllByCreatedAt(fromDate, toDate);
	      return ResponseEntity.ok(new GenericResponse(true, issuedcards));
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	              .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	  }

}
