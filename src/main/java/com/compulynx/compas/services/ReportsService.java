package com.compulynx.compas.services;

import org.springframework.stereotype.Service;

@Service
public class ReportsService {

//	private static final String logo_path = "/jasper/images/stackextend-logo.png";
//	private final String invoice_template_path = "/jasper/invoice_template.jrxml";
//
//    public void generateInvoiceFor(Customer order, Locale locale) throws IOException {
//
//        File pdfFile = File.createTempFile("my-invoice", ".pdf");
//
//        try(FileOutputStream pos = new FileOutputStream(pdfFile))
//        {
//			// Load the invoice jrxml template.
//            final JasperReport report = loadTemplate();
//
//              // Create parameters map.
//            final Map<String, Object> parameters = parameters(order, locale);
//
//            // Create an empty datasource.
//            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
//
//            // Render the PDF file
//            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
//
//        }
//        catch (final Exception e)
//        {
//            log.error(String.format("An error occured during PDF creation: %s", e));
//        }
//    }

}
