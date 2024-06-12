package com.compulynx.compas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.PrinterType;

@Repository
public interface PrinterTypeRepository  extends JpaRepository<PrinterType, Integer> {
	  @Query(nativeQuery = true, value = "select * from PrinterType ")
	List<PrinterType> findAlls();

	/**
	 * @param printerName
	 * @return
	 */
	PrinterType findByPrinterName(String printerName);

}
