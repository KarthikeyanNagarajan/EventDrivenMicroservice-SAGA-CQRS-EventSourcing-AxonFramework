package com.karthik.axonsaga.saga;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import com.karthik.model.ProductDto;
import com.karthik.model.UserDto;
import com.karthik.orderservice.commands.CancelOrderCommand;
import com.karthik.orderservice.commands.CompleteOrderCommand;
import com.karthik.orderservice.events.OrderCancelledEvent;
import com.karthik.orderservice.events.OrderCompletedEvent;
import com.karthik.orderservice.events.OrderCreatedEvent;
import com.karthik.paymentservice.commands.ValidatePaymentCommand;
import com.karthik.paymentservice.events.PaymentCancelledEvent;
import com.karthik.paymentservice.events.PaymentProcessedEvent;
import com.karthik.productservice.commands.BlockProductCommand;
import com.karthik.productservice.commands.ReleaseProductCommand;
import com.karthik.shipmentservice.commands.ShipOrderCommand;
import com.karthik.shipmentservice.events.OrderShippedEvent;
import com.karthik.userservice.commands.DeductWalletCommand;
import lombok.extern.slf4j.Slf4j;
//import com.karthik.userservice.commands.RefundWalletCommand;
//import com.karthik.paymentservice.commands.CancelPaymentCommand;

@Saga
@Slf4j
public class OrderProcessingSaga {

	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	@Qualifier("userclient")
	private WebClient userClient;
	
	@Autowired
	@Qualifier("productclient")
	private WebClient productClient;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	private void handle(OrderCreatedEvent event) {
		
		log.info("Starting Saga for order Id : {}", event.getOrderId());
		
		// Get User Details
		UserDto user = getUserById(event.getUserId());
		if(user == null)
		{
			cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! INVALID USERID !!!");
			return;
		}		
		
		// Get Product Details
		ProductDto product = getProductByName(event.getProductName());
		if(product == null)
		{
			cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! INVALID PRODUCTNAME !!!");
			return;
		}
		
		int userBalance = user.getWallet().getBalance();
		int orderQty = event.getQuantity();
		int productPrice = (int) Math.round(product.getPrice());
		int inventoryQty = product.getQty();
		int totalPrice = productPrice * orderQty;
		log.info("Saga -> UserBalance : {}", userBalance);
		log.info("Saga -> OrderQty : {}", orderQty);
		log.info("Saga -> ProductPrice : {}", productPrice);
		log.info("Saga -> InventoryQty : {}", inventoryQty);
		log.info("Saga -> TotalPrice : {}", totalPrice);
		
		// Check OrderQty In Inventory
		if(orderQty > inventoryQty)
		{
			cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! ORDER QTY IS MORE THAN AVAILABLE !!!");
			return;
		}
		
		// Block Product In Inventory
		BlockProductCommand(event, product);
		
		// Check UserWallet with TotalCost
		if(userBalance < totalPrice)
		{
			ReleaseProductCommand(event, product);
			cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! USER BALANCE IS NOT SUFFICIENT !!!");
			return;
		}
		
		// Deduct UserWallet by TotalCost
		try
		{
			DeductWalletCommand(user, totalPrice);
		}
		catch (Exception e)
		{
			cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! USER BALANCE IS NOT DEDUCTED !!!");
			return;
		}
		
		
		// Validate Payment
		ValidatePaymentCommand(event, user, totalPrice);
	}	

	@SagaEventHandler(associationProperty = "orderId")
	private void handle(PaymentProcessedEvent event) {
		
		log.info("Saga PaymentProcessedEvent for order Id : {}", event.getOrderId());

		ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
										.shipmentId(UUID.randomUUID().toString())
										.orderId(event.getOrderId())
										.shipmentStatus("SHIP ORDER COMMAND")
										.build();

		commandGateway.sendAndWait(shipOrderCommand);
	}

	@SagaEventHandler(associationProperty = "orderId")
	private void handle(OrderShippedEvent event) {
		
		log.info("Saga OrderShippedEvent for order Id : {}", event.getOrderId());

		CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
										.orderId(event.getOrderId())
										.orderStatus("COMPLETE ORDER COMMAND")
										.build();

		commandGateway.sendAndWait(completeOrderCommand);
	}

	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	private void handle(OrderCompletedEvent event) {
		
		log.info("Completed Saga for order Id : {}", event.getOrderId());
	}	

	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	private void handle(PaymentCancelledEvent event) {
		
		log.info("Saga PaymentCancelledEvent for order Id : {}", event.getOrderId());
		
		cancelOrderCommand(event.getOrderId(), "ORDER CANCELLED !!! PAYMENT ERROR !!!");
	}	

	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	private void handle(OrderCancelledEvent event) {
		
		log.info("Saga OrderCancelledEvent for order Id : {}", event.getOrderId());
	}
	
	private void cancelOrderCommand(String orderId, String orderStatus) {
		
		log.info("Saga CancelOrderCommand for order Id : {}", orderId);
		
		CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
				.orderId(orderId)
				.orderStatus(orderStatus)
				.build();
		
		commandGateway.sendAndWait(cancelOrderCommand);
	}
	
	private UserDto getUserById(int userId) {
		
		log.info("Saga Fetching UserDetails for UserId : {}", userId);
		
		UserDto user = null;
		
		try {
			
			user = userClient.get()
							.uri("/getUserById/{userId}", userId)
							.retrieve()
							.bodyToMono(UserDto.class)
							.block();
			
			log.info("Saga Fetched UserDetails : {}", user);
			
		} catch (Exception e) {
			log.error("Exception While Fetching UserId : {} !!!", userId);
			user = null;
		}
		
		return user;
	}
	
	private ProductDto getProductByName(String productName) {		
		
		log.info("Saga Fetching ProductDetails for productName : {}", productName);
		
		ProductDto product = null;
		
		try {
			
			product = productClient.get()
							.uri("/getProductByName/{productName}", productName)
							.retrieve()
							.bodyToMono(ProductDto.class)
							.block();
			
			log.info("Saga Fetched ProductDetails : {}", productName);
			
		} catch (Exception e) {
			log.error("Exception While Fetching productName : {} !!!", productName);
			product = null;
		}
		
		return product;
	}
	
	private void BlockProductCommand(OrderCreatedEvent event, ProductDto product) {
		
		log.info("Saga BlockProductCommand for Product Id : {}", product.getId());
		
		BlockProductCommand blockProductCommand = BlockProductCommand.builder()
											.id(product.getId())
											.qty(event.getQuantity())
											.build();
		commandGateway.sendAndWait(blockProductCommand);
	}
	
	private void ReleaseProductCommand(OrderCreatedEvent event, ProductDto product) {
		
		log.info("Saga ReleaseProductCommand for Product Id : {}", product.getId());
		
		ReleaseProductCommand releaseProductCommand = ReleaseProductCommand.builder()
											.id(product.getId())
											.qty(event.getQuantity())
											.build();
		commandGateway.sendAndWait(releaseProductCommand);
	}

	private void ValidatePaymentCommand(OrderCreatedEvent event, UserDto user, double totalPrice) {
		
		log.info("Saga ValidatePaymentCommand for Order Id : {}", event.getOrderId());
		
		ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
												.paymentId(UUID.randomUUID().toString())
												.orderId(event.getOrderId())
												.userDto(user)
												.totalPrice(totalPrice)
												.paymentStatus("VALIDATE PAYMENT COMMAND")
												.build();
		
		commandGateway.sendAndWait(validatePaymentCommand);
	}
	
	private void DeductWalletCommand(UserDto user, int totalPrice) {
		
		log.info("Saga DeductWalletCommand for Wallet Id : {}", user.getWallet().getWalletId());
				
		DeductWalletCommand deductWalletCommand = DeductWalletCommand.builder()
												.walletId(user.getWallet().getWalletId())
												.balance(totalPrice)
												.build();
		
		commandGateway.sendAndWait(deductWalletCommand);
	}
	
//	private void RefundWalletCommand(UserDto user, int totalPrice) {
//		
//		log.info("Saga RefundWalletCommand for Wallet Id : {}", user.getWallet().getWalletId());
//				
//		RefundWalletCommand refundWalletCommand = RefundWalletCommand.builder()
//												.walletId(user.getWallet().getWalletId())
//												.balance(totalPrice)
//												.build();
//		
//		commandGateway.sendAndWait(refundWalletCommand);
//	}
//	
//	private void cancelPaymentCommand(PaymentProcessedEvent event) {
//		
//		log.info("Saga PaymentProcessedEvent for order Id : {}", event.getOrderId());
//		
//		CancelPaymentCommand cancelPaymentCommand = CancelPaymentCommand.builder()
//											.paymentId(event.getPaymentId())
//											.orderId(event.getOrderId())
//											.paymentStatus("CANCEL PAYMENT COMMAND")
//											.build();
//		
//		commandGateway.sendAndWait(cancelPaymentCommand);
//	}
}
