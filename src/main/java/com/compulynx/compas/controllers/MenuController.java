package com.compulynx.compas.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.models.MenuHeaderMaster;
import com.compulynx.compas.services.MenuHeaderService;

@RestController
@RequestMapping(value=ResourceConfig.IPRINT_API_v1)
@CrossOrigin(origins = "*")
public class MenuController {
	@Autowired
	private MenuHeaderService headerService;
	
	@GetMapping("/menulist")
	public ResponseEntity<?> getHeaderMenus() {
		Collection<MenuHeaderMaster> menus = headerService.getMenuHeaders();
		
		if(menus.isEmpty()) {
			return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,404, 
					false, "no districts found",
					new HashSet<>(menus)), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,200,
				true, "menus found",
				new HashSet<>(menus)),HttpStatus.OK);
	}
	
	@GetMapping("/menulist/group")
	public ResponseEntity<?> getGroupMenus(
			@RequestParam(value="groupId") Long groupId) {

		List<MenuHeaderMaster> menus =headerService.getGroupMenus(groupId);
		
		if(menus.isEmpty()) {
			return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,404, 
					false, "no rights found",
					new HashSet<>(menus)), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV,200,
				true, "rights found",
				new HashSet<>(menus)),HttpStatus.OK);
	}
}
