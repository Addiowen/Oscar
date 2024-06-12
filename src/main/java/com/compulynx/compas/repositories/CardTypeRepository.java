package com.compulynx.compas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.CardType;
@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Integer>{

	/**
	 * @param name
	 * @return
	 */
	
	@Query(nativeQuery = true,value ="select * from CardTypeMaster where Name=?1")
	CardType findByName(String name);

}
