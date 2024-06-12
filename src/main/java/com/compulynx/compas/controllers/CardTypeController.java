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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.handlers.UserResponse;
import com.compulynx.compas.models.CardPrint;
import com.compulynx.compas.models.CardType;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.CardTypeService;

/**
 * @author KENSON
 *
 */
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class CardTypeController {
	@Autowired
	private CardTypeService cardTypeService;
	@Autowired
	  private AuditTrailService auditTrailService;
	  private final String module = "Card Type";
	 @PostMapping(value = "/cardType/create")
	  public ResponseEntity<?> createCardType(
	      @RequestBody CardType cardType,
	      @RequestHeader("principal") String principal,
	      HttpServletRequest rawRequest) { 
	    ResponseEntity responseEntity = null;
	    String actionStatus = "";
	    String failReason =null;
	    String action ="create card type";
	    CardType cardtype= cardTypeService.findByName(cardType.getName());
	    try {
	    	
	    	if(cardtype !=null && cardType.getId()==0){
	    	      actionStatus = "Failed";
	  	        failReason = "Duplicate card name creation detected";
	  	        responseEntity =
	  	            new ResponseEntity<>(
	  	                new UserResponse(UserResponse.APIV,
	  	                		203, 
	  	                		false,
	  	                		"card name already exists!"),
	  	                HttpStatus.OK);
	    	}
	    	else {
	   cardType = cardTypeService.addCardType(cardType);    
	    if (cardType == null) {
	    	actionStatus="failed";
	      responseEntity =
	          new ResponseEntity<>(
	              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to create card type"),
	              HttpStatus.OK);
	    } else {
	    	actionStatus="success";
	      responseEntity =
	          new ResponseEntity<>(
	              new CustomResponse(CustomResponse.APIV, 201, true, "card type updated successfully"),
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
	 @PostMapping(value = "/cardType/update")  
	  public ResponseEntity<?> updateCardType(
		      @RequestBody CardType cardType,
		      @RequestHeader("principal") String principal,
		      HttpServletRequest rawRequest) {
		    cardType = cardTypeService.updCardType(cardType);
		    ResponseEntity responseEntity = null;
		    String actionStatus = "";
		    String failReason =null;
		    String action ="update card type";
		    if (cardType == null) {
		    	actionStatus = "failed";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to update card type"),
		              HttpStatus.OK);
		    } else {
		    	actionStatus = "updated successfully";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 201, true, "card type updated successfully"),
		              HttpStatus.OK);
		 
		    }
		    auditTrailService.saveAuditTrail(
		            principal, rawRequest, module, action, null, null, actionStatus, failReason);
		    return responseEntity;
		  }
	    @GetMapping(path = "/cardTypes")
	    public ResponseEntity<?> fetchAll(
	    		@RequestHeader("principal") String principal,
	    		 HttpServletRequest rawRequest
	    		 ){
	    	 List<CardType> cardTypes = cardTypeService.getCardTypes();
	    	    ResponseEntity responseEntity = null;
	    	    String action ="Fetch card type";
	    	    String actionStatus = "";
			    String failReason =null;
	    	if (cardTypes.isEmpty()) {
	    		 actionStatus="No card Types found";
	    	      responseEntity =
	    	          new ResponseEntity<>(
	    	              new CustomResponse(
	    	                  CustomResponse.APIV, 404, false, "No card Types found", new HashSet<>(cardTypes)),
	    	              HttpStatus.NOT_FOUND);
	    	    } else {
	    	    	 actionStatus="Card Types found";
	    	      responseEntity =
	    	    		 
	    	          new ResponseEntity<>(
	    	              new CustomResponse(
	    	                  CustomResponse.APIV, 200, true, "card Types found", new HashSet<>(cardTypes)),
	    	              HttpStatus.OK);
	    	    }
	    	 auditTrailService.saveAuditTrail(
			            principal, rawRequest, module, action, null, null, actionStatus, failReason);
	    	    return responseEntity;
	    }
	    
		 @PostMapping(value = "/saveCard")
		  public ResponseEntity<?> saveCard(
		      @RequestBody CardPrint cardPrint,
		      @RequestHeader("principal") String principal,
		      HttpServletRequest rawRequest) {
			 System.out.println("<<<<<<<<<<<--Inside cardtype controller----->>>>>>>>>>" +cardPrint.accountName + " "+cardPrint.cardFormatId+" "+cardPrint.printedBy+" ");
			 cardPrint = cardTypeService.saveCard(cardPrint);
		    ResponseEntity responseEntity = null;
		    String actionStatus = "";
		    String failReason =null;
		    String action ="card print details saved ";
		    if (cardPrint == null) {
		    	actionStatus = "failed to save card details";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 203, false, "Failed to save card"),
		              HttpStatus.OK);
		    } else {
		    	actionStatus = "successully saved card details";
		      responseEntity =
		          new ResponseEntity<>(
		              new CustomResponse(CustomResponse.APIV, 201, true, "card saved successfully"),
		              HttpStatus.OK);
		 
		    }
		    auditTrailService.saveAuditTrail(
		            principal, rawRequest, module, action, null, null, actionStatus, failReason);
		    return responseEntity;
		  }
		  @GetMapping(value = "/cardsbyuser/report")
		  public ResponseEntity<?> getCardsPreviewByUser(
		      @RequestParam(value = "fromDate") String fromDate,
		      @RequestParam(value = "toDate") String toDate,
		      @RequestParam(value = "user") String userProp) {
		    try {
		      System.out.println("From Date:: " + fromDate);
		      System.out.println("To Date:: " + toDate);

		      List<CardPrint> cards = cardTypeService.getIssuedCardByUser(fromDate, toDate,userProp);

		      if (cards.size() <= 0) {
		        System.out.println("No Data");
		        return new ResponseEntity<>(cards, HttpStatus.OK);
		      }

		      System.out.println("Has issued Card Data");
		      return new ResponseEntity<>(cards, HttpStatus.OK);
		    } catch (Exception e) {
		      System.out.println(e.getMessage());
		      return new ResponseEntity<>(
		          new CustomResponse(CustomResponse.APIV, 200, false, "Server error processing request"),
		          HttpStatus.OK);
		    }
		  }		  
		  @GetMapping(value = "/cardsbybranch/report")
		  public ResponseEntity<?> getCardsPreviewByBranch(
			      @RequestParam(value = "fromDate") String fromDate,
			      @RequestParam(value = "toDate") String toDate,
			      @RequestParam(value = "branch") String branch) {
			    try {
			      System.out.println("From Date:: " + fromDate);
			      System.out.println("To Date:: " + toDate);

			      List<CardPrint> cards = cardTypeService.getIssuedCardByBranch(fromDate, toDate,branch);

			      if (cards.size() <= 0) {
			        System.out.println("No Data");
			        return new ResponseEntity<>(cards, HttpStatus.OK);
			      }

			      System.out.println("Has issued Card Data");
			      return new ResponseEntity<>(cards, HttpStatus.OK);
			    } catch (Exception e) {
			      System.out.println(e.getMessage());
			      return new ResponseEntity<>(
			          new CustomResponse(CustomResponse.APIV, 200, false, "Server error processing request"),
			          HttpStatus.OK);
			    }
			  }
		  @GetMapping(value = "/cardsbydate/report")
		  public ResponseEntity<?> getCardsPreviByDate(
			      @RequestParam(value = "fromDate") String fromDate,
			      @RequestParam(value = "toDate") String toDate) {
			    try {
			      System.out.println("From Date:: " + fromDate);
			      System.out.println("To Date:: " + toDate);

			      List<CardPrint> cards = cardTypeService.getIssuedCardByDate(fromDate, toDate);

			      if (cards.size() <= 0) {
			        System.out.println("No Data");
			        return new ResponseEntity<>(cards, HttpStatus.OK);
			      }

			      System.out.println("Has issued Card Data");
			      return new ResponseEntity<>(cards, HttpStatus.OK);
			    } catch (Exception e) {
			      System.out.println(e.getMessage());
			      return new ResponseEntity<>(
			          new CustomResponse(CustomResponse.APIV, 200, false, "Server error processing request"),
			          HttpStatus.OK);
			    }
			  }
}
