package com.karthik.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
@Value
public class OrderCreatedEvent {

	private String orderId;
	private int userId;
	private String productName;
	private int quantity;
	private String orderStatus;
}
