package com.karthik.axonsaga.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.karthik.orderservice.commands.CancelOrderCommand;
import com.karthik.orderservice.commands.CompleteOrderCommand;
import com.karthik.orderservice.commands.CreateOrderCommand;
import com.karthik.orderservice.events.OrderCancelledEvent;
import com.karthik.orderservice.events.OrderCompletedEvent;
import com.karthik.orderservice.events.OrderCreatedEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;
	private int userId;
	private String productName;
	private int quantity;
	private String orderStatus;

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		
		System.out.println("CreateOrderCommand handle");
		
		OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
				.orderId(createOrderCommand.getOrderId())
				.userId(createOrderCommand.getUserId())
				.quantity(createOrderCommand.getQuantity())
				.productName(createOrderCommand.getProductName())
				.orderStatus("ORDER CREATED EVENT")
				.build();

		AggregateLifecycle.apply(orderCreatedEvent);
	}

	@EventSourcingHandler
	public void on(OrderCreatedEvent orderCreatedEvent) {
		
		System.out.println("OrderCreatedEvent @EventSourcingHandler on");
		
		this.orderId = orderCreatedEvent.getOrderId();
		this.userId = orderCreatedEvent.getUserId();
		this.productName = orderCreatedEvent.getProductName();
		this.quantity = orderCreatedEvent.getQuantity();
		this.orderStatus = orderCreatedEvent.getOrderStatus();
	}
	
	@CommandHandler
	public void handle(CancelOrderCommand cancelOrderCommand) {
		
		System.out.println("CancelOrderCommand handle");
		
		OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();		
		BeanUtils.copyProperties(cancelOrderCommand, orderCancelledEvent);
		AggregateLifecycle.apply(orderCancelledEvent);
	}

	@EventSourcingHandler
	public void on(OrderCancelledEvent orderCancelledEvent) {
		
		System.out.println("OrderCancelledEvent @EventSourcingHandler on");
		
		this.orderStatus = orderCancelledEvent.getOrderStatus();
	}

	@CommandHandler
	public void handle(CompleteOrderCommand completeOrderCommand) {
		
		System.out.println("CompleteOrderCommand handle");
		
		OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
									.orderId(completeOrderCommand.getOrderId())
									.orderStatus("ORDER COMPLETED EVENT")
									.build();
		
		AggregateLifecycle.apply(orderCompletedEvent);
	}

	@EventSourcingHandler
	public void on(OrderCompletedEvent orderCompletedEvent) {
		
		System.out.println("OrderCompletedEvent @EventSourcingHandler on");
		
		this.orderStatus = orderCompletedEvent.getOrderStatus();
	}	
}
