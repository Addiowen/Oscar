package com.compulynx.compas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail,Long> {
	
	@Query(nativeQuery = true, value="select u.id,u.branch_id,u.fail_reason,u.new_value,u.previous_value,u.remote_ip,u.request_id, "
			+ " u.module,u.user_id,u.date,b.BranchName as branchName,u.action,u.action_status,u.remote_ip,u.server_ip from audit_logs "
			+ " u inner join BranchMaster b on b.branchCode=u.branch_id where cast(u.date as date) BETWEEN ?1 AND ?2 AND u.branch_id LIKE ?3 AND u.user_id LIKE ?4 order by u.date desc ")
	List<AuditTrail> getAll(String fromDate, String toDate, String branch, String userId);
	
}
