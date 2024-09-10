package com.karthik.productservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReleaseProductCommand {

	@TargetAggregateIdentifier
	private String id;
	private int qty;
}
