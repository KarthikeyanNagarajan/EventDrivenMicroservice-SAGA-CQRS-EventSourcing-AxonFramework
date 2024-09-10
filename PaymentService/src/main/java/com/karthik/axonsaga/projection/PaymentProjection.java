package com.karthik.axonsaga.projection;

import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Payment;
import com.karthik.axonsaga.repository.PaymentRepository;
import com.karthik.paymentservice.queries.GetPaymentDetailsByIdQuery;
import com.karthik.paymentservice.queries.GetPaymentDetailsQuery;

@Component
public class PaymentProjection {

	@Autowired
	PaymentRepository paymentRepository;

	@QueryHandler
	public List<Payment> getPaymentDetailsQuery(GetPaymentDetailsQuery query) {

		return paymentRepository.findAll();
	}

	@QueryHandler
	public Payment getPaymentDetailsByIdQuery(GetPaymentDetailsByIdQuery query) {

		return paymentRepository.findById(query.getPaymentId()).orElse(null);
	}
}
