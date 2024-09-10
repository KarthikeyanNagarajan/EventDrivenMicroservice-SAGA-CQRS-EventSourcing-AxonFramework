package com.karthik.axonsaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.karthik.axonsaga.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	
}
