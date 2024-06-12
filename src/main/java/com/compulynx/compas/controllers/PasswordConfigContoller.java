package com.compulynx.compas.controllers;

import com.compulynx.compas.services.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.models.GenericResponse;
import com.compulynx.compas.models.PasswordConfig;
import com.compulynx.compas.repositories.PasswordConfigRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class PasswordConfigContoller {

  @Autowired private PasswordConfigRepository passwordConfigRepository;
  @Autowired private AuditTrailService auditTrailService;

  private String module = "Password Configuration";

  @GetMapping("/getPasswordConfigs")
  public ResponseEntity<?> getPasswordConfig(
      HttpServletRequest rawRequest) {
    ResponseEntity responseEntity;

    try {
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new GenericResponse(true, passwordConfigRepository.findAll()));
    } catch (Exception e) {

      e.printStackTrace();
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new ErrorResponse(false, "303", "Error Getting Password Configs"));
    }
    return responseEntity;
  }
  

  @PostMapping("/savePasswordConfigs")
  public ResponseEntity<?> saveOrUpdatePasswordConfig(
      @RequestBody PasswordConfig passwordConfig,
      HttpServletRequest rawRequest,
      @RequestHeader("principal") String principal) {
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    try {
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new GenericResponse(true, passwordConfigRepository.save(passwordConfig)));
    } catch (Exception e) {
      e.printStackTrace();
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new ErrorResponse(false, "303", "Error Saving Password Configs"));
    }

    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        "Save Password Configs",
        null,
        null,
        actionStatus,
        failReason);
    return responseEntity;
  }
}
