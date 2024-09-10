
package com.karthik.axonsaga.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_table")
public class Payment {

	@Id
	@Column(name = "payment_id")
	private String paymentId;

	@Column(name = "order_id")
	private int orderId;

	@Column(name = "payment_time")
	private Date timestamp;

	@Column(name = "payment_status")
	private String paymentStatus;
}
