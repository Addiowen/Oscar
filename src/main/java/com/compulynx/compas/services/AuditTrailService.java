package com.compulynx.compas.services;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import com.compulynx.compas.models.Branch;
import com.compulynx.compas.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.AuditTrail;
import com.compulynx.compas.repositories.AuditTrailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuditTrailService {

	@Autowired
	private AuditTrailRepository auditTrailRepository;
	@Autowired private BranchService branchService;
	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(AuditTrailService.class);
	//fetch audit trail
	public List<AuditTrail> getAuditTrail(String fromDate, String toDate, String branch, String userName){
		String useraId;
		if(userName.length() > 0){
			User user = userService.findByUsername(userName);
			useraId = user.getId() + "%";
		} else {
			useraId = "%";
		}
		return auditTrailRepository.getAll(fromDate,toDate, branch + "%", useraId);
	}
	
	//save audit trail
	public AuditTrail saveAuditTrail(AuditTrail auditTrail){

		return auditTrailRepository.save(auditTrail);
	}

	public AuditTrail saveAuditTrail(
			String principal,
			HttpServletRequest request,
			String module,
			String action,
			String prevValue,
			String newValue,
			String actionStatus,
			String failReason) {
		logger.info("Login audit Trail Begin..");
		long userId = 0;
		String branchCode = "";
		User user = userService.findByUsername(principal);
		if(user != null) userId = user.getId();
		Branch branch = branchService.findBybranchCode(user.getBranchId());
		
		ObjectMapper mapper =new ObjectMapper();
		try {
			logger.info("Branch Details {}",mapper.writeValueAsString(branch));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Save to audit trail ==>> branch id {}",user.getBranchId());
		if(branch != null) branchCode = branch.getBranchCode();
		
		logger.info("Save to audit trail ==>> branch code {}",user.getBranchId());
		String remoteUrl = request.getRemoteAddr();
		String serverUrl = null;
		try {
			serverUrl = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			serverUrl = "UNKNOWN";
		}
		AuditTrail auditTrail =
				new AuditTrail(
						userId,
						module,
						new Date(),
						action,
						branchCode,
						remoteUrl,
						serverUrl,
						prevValue,
						newValue,
						actionStatus,
						failReason,
						request.getRequestedSessionId()
				);

		return auditTrailRepository.save(auditTrail);
	}
}
