package com.compulynx.compas.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author KENSON
 *
 */
@Service
public class GeneralReportsService {

	private JRXlsxExporter jRXlsxExporter;

	@Autowired
	public GeneralReportsService(JRXlsxExporter jRXlsxExporter) {
		this.jRXlsxExporter = jRXlsxExporter;
	}
	
	public ByteArrayOutputStream generate(GeneralReportsList reportsList, String templatePath)
			throws FileNotFoundException, JRException {

		System.out.println("############### GENERATING ###############");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = ResourceUtils.getFile(templatePath);
		JasperReport jasperReport;
		
		try {
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportsList.getReportData(), false);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("PoweredBy", "Compulynx");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			if ("p".equalsIgnoreCase(reportsList.getReportType())) {
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				return outputStream;
			} else if ("e".equalsIgnoreCase(reportsList.getReportType())) {
				JRXlsxExporter exporter = jRXlsxExporter;
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
				exporter.exportReport();
				return outputStream;
			}
			else {
				return null;
			}
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
