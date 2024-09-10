package com.karthik.paymentservice.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDetailsByIdQuery {

	private String paymentId;
}
