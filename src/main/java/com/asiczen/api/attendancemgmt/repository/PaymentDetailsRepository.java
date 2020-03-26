package com.asiczen.api.attendancemgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.PaymentDetails;

public interface PaymentDetailsRepository extends  JpaRepository<PaymentDetails,Long>{

}
