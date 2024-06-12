package com.compulynx.compas.handlers;

import java.util.Optional;
import java.util.Set;

import com.compulynx.compas.models.User;

public class CustomResponse {
	public static final String  APIV="1.0";
	private int respCode;
	private String respMessage;
	private boolean status;
	private String version;
	Set<?> collection;
	Optional<?> model;
	
	
	public CustomResponse(String version,int respCode, boolean status, String respMessage) {
		super();
		this.version = version;
		this.respCode = respCode;
		this.status = status;
		this.respMessage = respMessage;		
	}

	public CustomResponse(String version,int respCode,boolean status, String respMessage, Optional<?> model) {
		super();
		this.version = version;
		this.respCode = respCode;
		this.status = status;
		this.respMessage = respMessage;
		this.model = model;
	}
	
	public CustomResponse(String version,int respCode,boolean status, String respMessage, Set<?> collection) {
		super();
		this.version = version;
		this.respCode = respCode;
		this.status = status;
		this.respMessage = respMessage;		
		this.collection = collection;
	}
	
	public CustomResponse(String apiv2, int i, boolean b, String string, User userpro) {
		// TODO Auto-generated constructor stub
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getRespMessage() {
		return respMessage;
	}
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Set<?> getCollection() {
		return collection;
	}
	public void setCollection(Set<?> collection) {
		this.collection = collection;
	}
	public Optional<?> getModel() {
		return model;
	}
	public void setRespData(Optional<?> model) {
		this.model = model;
	}
	
		
}
