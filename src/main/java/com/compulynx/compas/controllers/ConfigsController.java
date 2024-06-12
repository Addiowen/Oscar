package com.compulynx.compas.controllers;

import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.models.GenericResponse;
import com.compulynx.compas.services.ConfigsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class ConfigsController {
  private ConfigsService configsService;

  @Autowired
  public ConfigsController(ConfigsService conf) {
    configsService = conf;
  }

  @GetMapping("/getConfigs")
  public ResponseEntity<?> getConfigs() {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new GenericResponse(true, configsService));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(
              new ErrorResponse(false, "500", "Failed to initialize configs - " + e.getMessage()));
    }
  }
}
