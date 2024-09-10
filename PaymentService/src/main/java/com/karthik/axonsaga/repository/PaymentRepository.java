package com.karthik.axonsaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.karthik.axonsaga.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
