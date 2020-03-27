package com.asiczen.api.attendancemgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.PayStructure;

@Repository
public interface PayStructureRepository extends  JpaRepository<PayStructure,Long>{

}
