package com.karthik.axonsaga.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.karthik.shipmentservice.commands.ShipOrderCommand;
import com.karthik.shipmentservice.events.OrderShippedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class ShipmentAggregate {

	@AggregateIdentifier
	private String shipmentId;
	private String orderId;
	private String shipmentStatus;
	
	@CommandHandler
	public ShipmentAggregate(ShipOrderCommand command) {
		
		System.out.println("ShipOrderCommand handle");
		
		OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
									.shipmentId(command.getShipmentId())
									.orderId(command.getOrderId())
									.shipmentStatus("ORDER SHIPPED EVENT")
									.build();
		
		AggregateLifecycle.apply(orderShippedEvent);
	}
	
	@EventSourcingHandler
	public void on(OrderShippedEvent event)
	{		
		System.out.println("OrderShippedEvent @EventSourcingHandler on");
		
		this.shipmentId = event.getShipmentId();
		this.orderId = event.getOrderId();
		this.shipmentStatus = event.getShipmentStatus();
	}	
}
