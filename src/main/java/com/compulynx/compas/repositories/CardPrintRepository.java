package com.compulynx.compas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.CardPrint;
import com.compulynx.compas.models.CardType;
@Repository
public interface CardPrintRepository  extends JpaRepository<CardPrint, Integer>{
	  @Query(
		      nativeQuery = true,
		      value =  "   select a.id,a.cardFormatId,a.printedBy,a.printerTypeId,a.accountName,c.BranchName,a.datePrinted  from CardPrint a, users b,BranchMaster c\r\n"
		      		+ "      WHERE b.id=a.printedBy  and b.branchId=c.id and convert(varchar(10),a.printedBy)=?3 and\r\n"
		      		+ "   cast(a.datePrinted as date) BETWEEN ?1 AND ?2 order by a.datePrinted asc")
		  List<CardPrint> getCardsByUser(String fromDate, String toDate,String userProp);
	  @Query(
		      nativeQuery = true,
		      value = "   select a.id,a.cardFormatId,a.printedBy,a.printerTypeId,a.accountName,c.BranchName,a.datePrinted  from CardPrint a, users b,BranchMaster c\r\n"
		      		+ "		      	WHERE  b.id=a.printedBy  and b.branchId=c.id and c.ID=?3 and\r\n"
		      		+ "		      		cast(a.datePrinted as date) BETWEEN ?1 AND ?2 order by a.datePrinted asc")
	List<CardPrint> getAllCardByBranch(String fromDate, String toDate, String branch);
	  
	  @Query(
		      nativeQuery = true,
		      value = "select (select count (*) from CardPrint WHERE DATEADD(dd, DATEDIFF(dd, 0,DatePrinted), 0) between\r\n"
		      		+ "                       ?1\r\n"
		      		+ "                        AND ?2  ) printTotal,a.AccountName,a.id,a.cardFormatId,a.printedBy,a.printerTypeId,b.username,a.PrinterSerialNo,a.PrintStatus,c.BranchName,c.BranchCode,a.DatePrinted  from CardPrint a, users b,BranchMaster c\r\n"
		      		+ "      WHERE  b.id=a.PrintedBy and b.branchId=c.id  and\r\n"
		      		+ "      cast(a.datePrinted as date) BETWEEN ?1 AND ?2   order by a.DatePrinted asc")  
	List<CardPrint> getAllCardByDate(String fromDate, String toDate);
	/**
	 * @param name
	 * @return
	 */
}
