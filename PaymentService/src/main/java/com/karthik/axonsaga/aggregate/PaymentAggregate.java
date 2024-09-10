package com.karthik.axonsaga.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.karthik.paymentservice.commands.CancelPaymentCommand;
import com.karthik.paymentservice.commands.ValidatePaymentCommand;
import com.karthik.paymentservice.events.PaymentCancelledEvent;
import com.karthik.paymentservice.events.PaymentProcessedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class PaymentAggregate {

	@AggregateIdentifier
	private String paymentId;
	private String orderId;
	private String paymentStatus;

	@CommandHandler
	public PaymentAggregate(ValidatePaymentCommand command) {
		
		System.out.println("ValidatePaymentCommand handle");
		
		PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
				.paymentId(command.getPaymentId())
				.orderId(command.getOrderId())
				.paymentStatus("PAYMENT PROCESSED EVENT")
				.build();

		AggregateLifecycle.apply(paymentProcessedEvent);
	}
	
	@EventSourcingHandler
	public void on(PaymentProcessedEvent event) {
		
		System.out.println("PaymentProcessedEvent @EventSourcingHandler on");
		
		this.paymentId = event.getPaymentId();
		this.orderId = event.getOrderId();
		this.paymentStatus = event.getPaymentStatus();
	}
	
	@CommandHandler
	public void handle(CancelPaymentCommand command) {
		
		System.out.println("CancelPaymentCommand handle");
		
		PaymentCancelledEvent paymentCancelledEvent = PaymentCancelledEvent.builder()
											.orderId(command.getOrderId())
											.paymentId(command.getPaymentId())
											.paymentStatus("PAYMENT CANCELLED EVENT")
											.build();

		AggregateLifecycle.apply(paymentCancelledEvent);
	}

	@EventSourcingHandler
	public void on(PaymentCancelledEvent event) {
		
		System.out.println("PaymentCancelledEvent @EventSourcingHandler on");
		
		this.paymentStatus = event.getPaymentStatus();
	}
}
