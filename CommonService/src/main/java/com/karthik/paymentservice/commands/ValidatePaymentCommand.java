package com.karthik.paymentservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.karthik.model.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatePaymentCommand {

	@TargetAggregateIdentifier
	private String paymentId;
	private String orderId;
	private UserDto userDto;
	private double totalPrice;
	private String paymentStatus;
}
