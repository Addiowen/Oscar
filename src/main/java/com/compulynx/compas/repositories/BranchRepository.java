package com.compulynx.compas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
	List<Branch> findAll();
	
	@Query("select count(u) from Branch u")
	int getBranchCount();
	
	@Query(nativeQuery = true,value="select * from BranchMaster where BranchName=?1")
	Branch findByBranchName(String BranchName);
	
	Branch findByBranchCode(String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT *FROM BranchMaster WHERE id=?1")
	Branch findById(String string);
}
