package com.karthik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private int orderId;
	private int userId;
	private String productName;
	private int quantity;
}
