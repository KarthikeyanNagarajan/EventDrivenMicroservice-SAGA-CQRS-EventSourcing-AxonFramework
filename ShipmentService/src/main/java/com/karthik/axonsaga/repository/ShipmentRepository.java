package com.karthik.axonsaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.karthik.axonsaga.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

}
