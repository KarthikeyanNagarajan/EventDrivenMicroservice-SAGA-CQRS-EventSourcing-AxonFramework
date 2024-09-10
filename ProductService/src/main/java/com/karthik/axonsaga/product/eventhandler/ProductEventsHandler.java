package com.karthik.axonsaga.product.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Product;
import com.karthik.axonsaga.repository.ProductRepository;
import com.karthik.productservice.commands.ReleaseProductCommand;
import com.karthik.productservice.events.ProductBlockedEvent;

@Component
public class ProductEventsHandler {

	@Autowired
	private ProductRepository productRepository;
	
	@EventHandler
	public void on(ProductBlockedEvent event)
	{
		Product product = productRepository.findById(event.getId()).get();
		product.setQty(product.getQty() - event.getQty());		
		productRepository.save(product);
	}
	
	@EventHandler
	public void on(ReleaseProductCommand event)
	{
		Product product = productRepository.findById(event.getId()).get();
		product.setQty(product.getQty() + event.getQty());
		productRepository.save(product);
	}
}
