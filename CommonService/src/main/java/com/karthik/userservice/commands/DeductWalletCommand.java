package com.karthik.userservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeductWalletCommand {

	@TargetAggregateIdentifier
	private String walletId;
	private int balance;
}
