package com.karthik.axonsaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.axonsaga.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

}
