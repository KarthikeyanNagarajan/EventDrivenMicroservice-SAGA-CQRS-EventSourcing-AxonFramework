package com.karthik.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentDto {

	private int shipmentId;
	private String orderId;
	private String shipmentStatus;
}
