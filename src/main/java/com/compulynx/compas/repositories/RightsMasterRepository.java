package com.compulynx.compas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compulynx.compas.models.RightMaster;

@Repository
public interface RightsMasterRepository extends JpaRepository<RightMaster, Long> {

}
