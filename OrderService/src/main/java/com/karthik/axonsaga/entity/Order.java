package com.karthik.axonsaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_table")
public class Order {

	@Id	
	@Column(name = "order_id")
	private String orderId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "order_quantity")
	private int quantity;

	@Column(name = "order_status")
	private String orderStatus;
}
