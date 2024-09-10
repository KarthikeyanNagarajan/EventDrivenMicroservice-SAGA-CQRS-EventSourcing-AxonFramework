package com.karthik.paymentservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class CancelPaymentCommand {

	@TargetAggregateIdentifier
	private String paymentId;
	private String orderId;
	private String paymentStatus;
}
