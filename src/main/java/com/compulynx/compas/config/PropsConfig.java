package com.compulynx.compas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropsConfig {


  @Value("${password_expiry}")
  private int passExpiryDays;


  @Value("${report_url}")
  private String reportUrl;
  

  


  public PropsConfig() {}



  public int getPassExpiryDays() {
    return passExpiryDays;
  }

  public String getReportUrl() {
    return reportUrl;
  }

@Override
public String toString() {
	return "PropsConfig [passExpiryDays=" + passExpiryDays + ", reportUrl=" + reportUrl + "]";
}



}
