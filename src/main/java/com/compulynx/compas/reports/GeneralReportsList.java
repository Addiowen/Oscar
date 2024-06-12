package com.compulynx.compas.reports;

import java.util.List;

public class GeneralReportsList {

	private String reportType;
	private List<Object> reportData;
	
	public GeneralReportsList() {
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<Object> getReportData() {
		return reportData;
	}

	public void setReportData(List<Object> reportData) {
		this.reportData = reportData;
	}

	@Override
	public String toString() {
		return "GeneralReportsList [reportType=" + reportType + ", reportData=" + reportData + "]";
	}
	
}
