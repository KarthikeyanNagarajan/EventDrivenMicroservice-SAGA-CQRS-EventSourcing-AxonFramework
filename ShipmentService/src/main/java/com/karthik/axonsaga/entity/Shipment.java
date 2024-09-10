
package com.karthik.axonsaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shipment_table")
public class Shipment {

	@Id
	@Column(name = "shipment_id")
	private String shipmentId;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "shipment_status")
	private String shipmentStatus;
}
