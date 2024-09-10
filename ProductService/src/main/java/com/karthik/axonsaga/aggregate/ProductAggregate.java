package com.karthik.axonsaga.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.karthik.productservice.commands.BlockProductCommand;
import com.karthik.productservice.commands.ReleaseProductCommand;
import com.karthik.productservice.events.ProductBlockedEvent;
import com.karthik.productservice.events.ProductReleasedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class ProductAggregate {

	@AggregateIdentifier
	private String id;
	private int qty;

	@CommandHandler
	public ProductAggregate(BlockProductCommand command) {
		
		System.out.println("BlockProductCommand handle");
		
		ProductBlockedEvent productBlockedEvent = ProductBlockedEvent.builder()
												.id(command.getId())
												.qty(command.getQty())
												.build();
		
		AggregateLifecycle.apply(productBlockedEvent);
	}

	@EventSourcingHandler
	public void on(ProductBlockedEvent event) {
		
		System.out.println("ProductBlockedEvent @EventSourcingHandler on");
		
		this.id = event.getId();
		this.qty = event.getQty();
	}
	
	@CommandHandler
	public void handle(ReleaseProductCommand command) {
		
		System.out.println("ReleaseProductCommand handle");
		
		ProductReleasedEvent productReleasedEvent = ProductReleasedEvent.builder()
												.id(command.getId())
												.qty(command.getQty())
												.build();

		AggregateLifecycle.apply(productReleasedEvent);
	}

	@EventSourcingHandler
	public void on(ProductReleasedEvent event) {
		
		System.out.println("ProductReleasedEvent @EventSourcingHandler on");
		
		this.qty = event.getQty();
	}
}
