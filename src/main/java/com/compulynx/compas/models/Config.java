package com.compulynx.compas.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="config")
public class Config extends BaseModel {
	
	@Column(name="afis")
	private String afisUri;
	
	@Column(name="core_banking")
	private String coreBankingUri;
	
	@Column(name="defaults",columnDefinition="tinyint")
	private boolean defaultConf=false;
	
	protected Config() {
		super();
	}

	public Config(String afisUri, String coreBankingUri, boolean defaultConf) {
		super();
		this.afisUri = afisUri;
		this.coreBankingUri = coreBankingUri;
		this.defaultConf = defaultConf;
	}
	
	@JsonIgnore
	@Override
	public Long getId() {
		return super.getId();
	}

	public String getAfisUri() {
		return afisUri;
	}

	public void setAfisUri(String afisUri) {
		this.afisUri = afisUri;
	}

	public String getCoreBankingUri() {
		return coreBankingUri;
	}
	
	public void setCoreBankingUri(String coreBankingUri) {
		this.coreBankingUri = coreBankingUri;
	}
	
	@JsonIgnore
	public boolean isDefaultConf() {
		return defaultConf;
	}

	public void setDefaultConf(boolean defaultConf) {
		this.defaultConf = defaultConf;
	}
	
		
}
