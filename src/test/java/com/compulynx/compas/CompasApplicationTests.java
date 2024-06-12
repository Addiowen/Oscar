package com.compulynx.compas;

import com.compulynx.compas.models.AccountDetailObject;
import com.compulynx.compas.services.HttpService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CompasApplicationTests {

	@Autowired
	 public HttpService service;

	@Test
	public void contextLoads() {
	}
	@Test
	public void getAccountDetails(){
		String endpointUrl = "http://197.220.114.46:9005/adaptor/cbs/CRM/AccountDetails";
		JsonNode node = service.sendApiCallRequest(HttpMethod.POST,endpointUrl, AccountDetailObject.builder().customerAccount("string").customerCode("string").userId("string").productType("string").build());
		log.info("Response {}",node);
	}

}
