package com.karthik.axonsaga.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.karthik.userservice.commands.DeductWalletCommand;
import com.karthik.userservice.commands.RefundWalletCommand;
import com.karthik.userservice.events.WalletDeductedEvent;
import com.karthik.userservice.events.WalletRefundedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class UserWalletAggregate {

	@AggregateIdentifier
	private String walletId;
	private int balance;

	@CommandHandler
	public UserWalletAggregate(DeductWalletCommand command) {
		
		System.out.println("DeductWalletCommand handle");
				
		WalletDeductedEvent walletDeductedEvent = WalletDeductedEvent.builder()
												.walletId(command.getWalletId())
												.balance(command.getBalance())
												.build();
		
		AggregateLifecycle.apply(walletDeductedEvent);
	}

	@EventSourcingHandler
	public void on(WalletDeductedEvent event) {
		
		System.out.println("WalletDeductedEvent @EventSourcingHandler on");
		
		this.walletId = event.getWalletId();
		this.balance = event.getBalance();
	}
	
	@CommandHandler
	public void handle(RefundWalletCommand command) {
		
		System.out.println("RefundWalletCommand handle");
		
		WalletRefundedEvent walletRefundedEvent = WalletRefundedEvent.builder()
											.walletId(command.getWalletId())
											.balance(command.getBalance())
											.build();

		AggregateLifecycle.apply(walletRefundedEvent);
	}

	@EventSourcingHandler
	public void on(WalletRefundedEvent event) {
		
		System.out.println("WalletRefundedEvent @EventSourcingHandler on");
		
		this.balance = event.getBalance();
	}
}
