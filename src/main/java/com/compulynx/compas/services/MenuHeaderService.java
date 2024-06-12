package com.compulynx.compas.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.MenuHeaderMaster;
import com.compulynx.compas.repositories.MenuHeaderRepository;

@Service
public class MenuHeaderService {
	
	@Autowired
	private MenuHeaderRepository headerRepository;
	
	public Collection<MenuHeaderMaster> getMenuHeaders() {
		return headerRepository.findAll();
	}

	public List<MenuHeaderMaster> getGroupMenus(Long groupId) {
		// TODO Auto-generated method stub
		return headerRepository.getAll(groupId);
	}
}
