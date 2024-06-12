package com.compulynx.compas.controllers;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.models.RightMaster;
import com.compulynx.compas.services.RightsMasterService;

@RestController
@RequestMapping(value=ResourceConfig.IPRINT_API_v1)
public class RIghtMasterController {
	
	@Autowired
	private RightsMasterService masterService;
	
	@GetMapping(value="/rightsmenulist")
	public ResponseEntity<?> getRights() {
		List<RightMaster> rights = masterService.getRights();
		
		if(rights.isEmpty()) {
			return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,
					404, false, "no rights found"),HttpStatus.OK);
			
			
		}
		return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,
				200, true, "rights found",new HashSet<>(rights)),HttpStatus.OK);
	}
}
