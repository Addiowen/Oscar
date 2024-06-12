package com.compulynx.compas.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.config.PropsConfig;

@Service
public class ConfigsService {
  private PropsConfig propsConfig;

  @Autowired
  public ConfigsService(PropsConfig propsConfig) {
    this.propsConfig = propsConfig;
  }


  public String getReportUrl() {
    return propsConfig.getReportUrl();
  }


@Override
public String toString() {
	return "ConfigsService [propsConfig=" + propsConfig + ", getReportUrl()=" + getReportUrl() + ", getClass()="
			+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
}
 
}
