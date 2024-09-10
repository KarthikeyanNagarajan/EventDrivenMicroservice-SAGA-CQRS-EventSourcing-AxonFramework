package com.karthik.axonsaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "product_table")
public class Product {

	@Id
	@Column(name = "product_id")
	private String id;

	@Column(name = "product_name")
	private String name;

	@Column(name = "product_price")
	private double price;

	@Column(name = "product_qty")
	private int qty;
}