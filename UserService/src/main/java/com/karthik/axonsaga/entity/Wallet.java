package com.karthik.axonsaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "wallet_table")
public class Wallet {

	@Id
	@Column(name = "wallet_id")
	private String walletId;

	@Column(name = "wallet_balance")
	private int balance;

	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
}
