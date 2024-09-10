package com.karthik.axonsaga.payment.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Payment;
import com.karthik.axonsaga.repository.PaymentRepository;
import com.karthik.paymentservice.events.PaymentCancelledEvent;
import com.karthik.paymentservice.events.PaymentProcessedEvent;

@Component
public class PaymentEventsHandler {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@EventHandler
	public void on(PaymentProcessedEvent event)
	{
		Payment payment = new Payment();		
		BeanUtils.copyProperties(event, payment);
		paymentRepository.save(payment);
	}
	
	@EventHandler
	public void on(PaymentCancelledEvent event)
	{
		Payment payment = paymentRepository.findById(event.getPaymentId()).get();
		payment.setPaymentStatus(event.getPaymentStatus());
		paymentRepository.save(payment);
	}
}
