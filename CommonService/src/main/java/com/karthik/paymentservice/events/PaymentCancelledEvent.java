package com.karthik.paymentservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCancelledEvent {

	private String paymentId;
	private String orderId;
	private String paymentStatus;
}
