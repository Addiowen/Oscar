package com.compulynx.compas.controllers;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.models.AccountDetailObject;
import com.compulynx.compas.models.AuditTrail;
import com.compulynx.compas.models.PrintRequestBody;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.HttpService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class CustomerDetailsController {
  private final HttpService httpService;

  public AuditTrail auditTrail;
  private final String module = "customerDetails";
  @Autowired
  private AuditTrailService auditTrailService;
  private Logger logger = LoggerFactory.getLogger(CustomerDetailsController.class);

  @Autowired
  private RestTemplate restTemplate; // This is for making HTTP requests

  @PostMapping(value = "/customer/details")
  public ResponseEntity<?> getCustomerDetails(
          @RequestBody PrintRequestBody printRequestBody,
          HttpServletRequest rawRequest) {

    logger.info("Request Body: " + printRequestBody);

    // Endpoint URL to call
    String endpointUrl = "http://197.220.114.46:9005/adaptor/cbs/CRM/AccountDetails";
   JsonNode node = httpService.sendApiCallRequest(HttpMethod.POST,endpointUrl, AccountDetailObject.builder().customerAccount(printRequestBody.getCustomerAccount()).customerCode(printRequestBody.getCustomerCode()).userId("").productType("").build());

    // Call the endpoint and retrieve the response
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, printRequestBody, String.class);

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      String responseBody = responseEntity.getBody();
      logger.info("Response Body: " + responseBody);

      // Parse the response body using Gson
      JsonObject jsonResponse = new JsonObject();
      JsonObject data = jsonResponse.getAsJsonObject("data");

      // Extract relevant data fields
      String accountName = data.get("firstName").getAsString() + " " + data.get("lastName").getAsString();
      String accountNumber = data.get("accountNumber").getAsString();

      // Format the data for iPrint solution
      JsonObject formattedResponse = new JsonObject();
      formattedResponse.addProperty("accountName", accountName);
      formattedResponse.addProperty("accountNumber", accountNumber);

      // Forward the formatted response to the frontend
      return new ResponseEntity<>(formattedResponse.toString(), HttpStatus.OK);
    } else {
      // Handle error response from the endpoint
//      ErrorResponse errorResponse = new ErrorResponse(false,500, responseEntity.getStatusCodeValue());
//      return new ResponseEntity<>(errorResponse, responseEntity.getStatusCode());
      return ResponseEntity.ok("");
    }
  }
}


