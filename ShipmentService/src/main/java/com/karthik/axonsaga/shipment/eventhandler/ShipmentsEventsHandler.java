package com.karthik.axonsaga.shipment.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Shipment;
import com.karthik.axonsaga.repository.ShipmentRepository;
import com.karthik.shipmentservice.events.OrderShippedEvent;

@Component
public class ShipmentsEventsHandler {

	@Autowired
	private ShipmentRepository shipmentRepository;	
	
	@EventHandler
	public void on(OrderShippedEvent event)
	{
		Shipment shipment = new Shipment();
		BeanUtils.copyProperties(event, shipment);
		shipmentRepository.save(shipment);
	}
}
