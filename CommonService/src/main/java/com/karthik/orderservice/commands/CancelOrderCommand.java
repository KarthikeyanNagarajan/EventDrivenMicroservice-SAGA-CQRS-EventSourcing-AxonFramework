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
public class CancelOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
	private String orderStatus;
}
