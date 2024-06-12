package com.compulynx.compas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.Branch;
import com.compulynx.compas.repositories.BranchRepository;

@Service
public class BranchService {
	@Autowired
	private BranchRepository branchRepository;
	
	public List<Branch> branches() {
		return branchRepository.findAll();
	}

	public Branch addBranch(Branch branch) {
		return branchRepository.save(branch);
	}

	public Branch findBybranchCode(String branchCode){
		return branchRepository.findByBranchCode(branchCode);
	}

	public Branch updBranch(Branch branch) {
		// TODO Auto-generated method stub
		Branch brc=branchRepository.findById(branch.getId().toString());
		brc.setId(branch.getId());
		brc.setActive(branch.getActive());
		brc.setBranchCode(branch.getBranchCode());
		brc.setBranchAddress(branch.getBranchAddress());
		brc.setBranchName(branch.getBranchName());
		return branchRepository.save(brc);
	}

	/**
	 * @param branchName
	 * @return
	 */
	public Branch findByBranchName(String branchName) {
		// TODO Auto-generated method stub
		return branchRepository.findByBranchName(branchName);
	}

}
