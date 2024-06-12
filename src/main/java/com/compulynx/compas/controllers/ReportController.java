package com.compulynx.compas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.compulynx.compas.Responses.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.compulynx.compas.reports.GeneralReportsList;
import com.compulynx.compas.reports.GeneralReportsService;
import com.compulynx.compas.utils.AppUtilities;

@Controller
@RequestMapping("reports")
public class ReportController {
	  private GeneralReportsService generalReportsService;
	  @Autowired
	  public ReportController(GeneralReportsService generalReportsService) {  
	    this.generalReportsService = generalReportsService;
	  }
	  @PostMapping("/issuedCardReport")
	  public ResponseEntity<?> issuedCardReport(
	          @RequestBody GeneralReportsList issuedCardReport) {
	    HttpHeaders headers = new HttpHeaders();
	    MediaType mediaType = null;
	    String templatePath=null;
	    if ("p".equalsIgnoreCase(issuedCardReport.getReportType())) {
	    	System.out.println(AppUtilities.logPreString() + " Type : :P "+issuedCardReport.getReportType());
	      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=issuedCard.pdf");
	      mediaType = MediaType.parseMediaType("application/pdf");
	       templatePath = "classpath:Card_printed_pdf.jrxml";
	    } else {
	    	System.out.println(AppUtilities.logPreString() + " Type : :E "+issuedCardReport.getReportType());
	      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=issuedCard.xlsx");
	      mediaType =
	              MediaType.parseMediaType(
	                      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	     templatePath = "classpath:Card_printed_xlsx.jrxml";
	    }
	 
	    
	    byte[] finalReport = null;

	    try {
	      finalReport =
	              generalReportsService.generate(issuedCardReport, templatePath).toByteArray();
	      return ResponseEntity.ok().headers(headers).contentType(mediaType).body(finalReport);
	    } catch (Exception e) {
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.CREATED)
	              .body(new ErrorResponse(false, "303", e.getMessage()));
	    }
	  }  
	  @PostMapping("/dailyUserCreation")
	  public ResponseEntity<?> generateDailyUserCreation(
		      @RequestBody GeneralReportsList dailyUserCreationReportObj) {
		  
		  System.out.println("Inside export users report !!..");
		    HttpHeaders headers = new HttpHeaders();
		    MediaType mediaType = null;
		    String templatePath=null;
		    if ("p".equalsIgnoreCase(dailyUserCreationReportObj.getReportType())) {
		      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DailyUserCreation.pdf");
		      mediaType = MediaType.parseMediaType("application/pdf");
		      templatePath = "classpath:Daily_userCreation_pdf.jrxml";
		    } else {
		      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DailyUserCreation.xlsx");
		      mediaType =
		          MediaType.parseMediaType(
		              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		      templatePath = "classpath:Daily_userCreation_xlsx.jrxml";
		    }
		  
		    byte[] finalReport = null;

		    try {
		      finalReport =
		          generalReportsService.generate(dailyUserCreationReportObj, templatePath).toByteArray();
		      return ResponseEntity.ok().headers(headers).contentType(mediaType).body(finalReport);
		    } catch (Exception e) {
		      e.printStackTrace();
		      return ResponseEntity.status(HttpStatus.CREATED)
		          .body(new ErrorResponse(false, "303", e.getMessage()));
		    }
	  }
	  @PostMapping("/auditRailReport") 
	  public ResponseEntity<?> generateAuditrailreport(
		      @RequestBody GeneralReportsList auditTrailReport) {
		  
		  System.out.println("Inside export users report !!..");
		    HttpHeaders headers = new HttpHeaders();
		    MediaType mediaType = null;
		    String templatePath=null;
		    if ("p".equalsIgnoreCase(auditTrailReport.getReportType())) {
		      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AuditrailCreation.pdf");
		      mediaType = MediaType.parseMediaType("application/pdf");
		      templatePath = "classpath:Audit_Trail_pdf.jrxml";
		    } else {
		      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AuditrailCreation.xlsx");
		      mediaType =
		          MediaType.parseMediaType(
		              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		      templatePath = "classpath:Audit_Trail_xlsx.jrxml";
		    }
		  
		    byte[] finalReport = null;

		    try {
		      finalReport =
		          generalReportsService.generate(auditTrailReport, templatePath).toByteArray();
		      return ResponseEntity.ok().headers(headers).contentType(mediaType).body(finalReport);
		    } catch (Exception e) {
		      e.printStackTrace();
		      return ResponseEntity.status(HttpStatus.CREATED)
		          .body(new ErrorResponse(false, "303", e.getMessage()));
		    }
	  }
	  
}
