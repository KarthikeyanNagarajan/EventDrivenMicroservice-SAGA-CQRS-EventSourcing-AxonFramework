package com.karthik.axonsaga.controller;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.model.OrderDto;
import com.karthik.orderservice.commands.CreateOrderCommand;

@RestController
@RequestMapping("/orderservice")
public class OrderController {

	@Autowired
	private transient CommandGateway commandGateway;

	@PostMapping("/createOrder")
	public Object createOrder(@RequestBody OrderDto model)
	{	
		String orderId = UUID.randomUUID().toString().replace("-", "");
		System.out.println("OrderId -> " + orderId);
		
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
				.orderId(orderId)
				.userId(model.getUserId())
				.quantity(model.getQuantity())
				.productName(model.getProductName())
				.orderStatus("CREATE ORDER COMMAND")
				.build();
		
		System.out.println("CreateOrderCommand Created");

		return commandGateway.sendAndWait(createOrderCommand);
	}
}
