package com.karthik.axonsaga.order.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Order;
import com.karthik.axonsaga.repository.OrderRepository;
import com.karthik.orderservice.events.OrderCancelledEvent;
import com.karthik.orderservice.events.OrderCompletedEvent;
import com.karthik.orderservice.events.OrderCreatedEvent;

@Component
public class OrderEventsHandler {

	@Autowired
	private OrderRepository orderRepository;

	@EventHandler
	public void on(OrderCreatedEvent event) {
		
		Order order = new Order();
		BeanUtils.copyProperties(event, order);
		orderRepository.save(order);
	}

	@EventHandler
	public void on(OrderCompletedEvent event) {
		
		Order order = orderRepository.findById(event.getOrderId()).get();
		order.setOrderStatus(event.getOrderStatus());
		orderRepository.save(order);
	}

	@EventHandler
	public void on(OrderCancelledEvent event) {
		
		Order order = orderRepository.findById(event.getOrderId()).get();
		order.setOrderStatus(event.getOrderStatus());
		orderRepository.save(order);
	}
}
