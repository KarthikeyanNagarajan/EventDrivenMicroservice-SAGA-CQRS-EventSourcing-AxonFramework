package com.karthik.orderservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
@Value
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
	private int userId;
	private String productName;
	private int quantity;
	private String orderStatus;
}
