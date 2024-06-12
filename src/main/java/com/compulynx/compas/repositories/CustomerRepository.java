package com.compulynx.compas.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.IssuedCard;
import com.compulynx.compas.models.extras.CustomerStats;

@Repository
public interface CustomerRepository extends JpaRepository<IssuedCard, Long> {

	@Query("select count(u) from CardPrint u")
	int countIssuedCard();

	@Query(nativeQuery = true, value = "SELECT m.month , ISNULL(count(c.id), 0) COUNT " + "FROM months m "
			+ "left join  CardPrint c on m.month = MONTH(c.DatePrinted) " + "GROUP BY m.month ")
	List<CustomerStats> getYearlyCustomerStats();

	

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM CardPrint WHERE YEAR(DatePrinted) = ?1  AND MONTH(DatePrinted)=?2")
	int getMonthlyCount(int year, int month);

	


}
