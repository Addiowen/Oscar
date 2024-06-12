package com.compulynx.compas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.PrinterType;
import com.compulynx.compas.repositories.PrinterTypeRepository;

@Service
public class PrinterTypeService {
@Autowired
private PrinterTypeRepository printerTypeRepository;
	public PrinterType addPrinterType(PrinterType printerType) {
		return printerTypeRepository.save(printerType);
	}
	public PrinterType updPrinterType(PrinterType printerType) {
		PrinterType printer =new PrinterType();
		printer.setId(printerType.getId());
		printer.setDescription(printerType.getDescription());
		printer.setPrinterName(printerType.getPrinterName());
		return printerTypeRepository.save(printer);
	}
	public List<PrinterType> getPrinterTypes() {
		return printerTypeRepository.findAll();
	}
	/**
	 * @param printerName
	 * @return
	 */
	public PrinterType findByName(String printerName) {
		// TODO Auto-generated method stub
		return printerTypeRepository.findByPrinterName(printerName);
	}

}
