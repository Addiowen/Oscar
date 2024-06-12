package com.compulynx.compas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.compulynx.compas.models.Branch;
import com.compulynx.compas.models.IssuedCard;
import com.compulynx.compas.models.User;
@Repository
public interface IssuedCardRepository extends CrudRepository<IssuedCard, Long> {

	  @Query(
	          "from IssuedCard where cast(DatePrinted as date) between :fromDate and :toDate")
	  List<IssuedCard> findAllByCreatedAt(
	          @Param("fromDate") Date fromDate,
	          @Param("toDate") Date toDate);
	  
	  @Query(
	          "from IssuedCard where user = :user and cast(DatePrinted as date) between :fromDate and :toDate")
	  List<IssuedCard> findAllByUserAndCreatedAt(
	          @Param("user") User user,
	          @Param("fromDate") Date fromDate,
	          @Param("toDate") Date toDate);
	  
	  @Query(
		      "from IssuedCard where branch = :branch and user = :user and cast(DatePrinted as date) between :fromDate and :toDate")
		  List<IssuedCard> findAllByBranchAndUserAndCreatedBy(
		      @Param("branch") Branch branch,
		      @Param("user") User user,
		      @Param("fromDate") Date fromDate,
		      @Param("toDate") Date toDate);
	  
	  @Query("from IssuedCard where branch = :branch and DatePrinted between :fromDate and :toDate")
	  List<IssuedCard> findAllByBranchAndCreatedAt(
	      @Param("branch") Branch branch,
	      @Param("fromDate") Date fromDate,
	      @Param("toDate") Date toDate);

}
