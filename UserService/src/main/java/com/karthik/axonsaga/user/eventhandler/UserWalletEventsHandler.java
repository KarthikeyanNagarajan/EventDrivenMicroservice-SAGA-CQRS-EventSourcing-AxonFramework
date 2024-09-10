package com.karthik.axonsaga.user.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.Wallet;
import com.karthik.axonsaga.repository.WalletRepository;
import com.karthik.userservice.events.WalletDeductedEvent;
import com.karthik.userservice.events.WalletRefundedEvent;

@Component
public class UserWalletEventsHandler {

	@Autowired
	private WalletRepository walletRepository;
	
	@EventHandler
	public void on(WalletDeductedEvent event)
	{
		Wallet wallet = walletRepository.findById(event.getWalletId()).get();
		wallet.setBalance(wallet.getBalance() - event.getBalance());;
		walletRepository.save(wallet);
	}
	
	@EventHandler
	public void on(WalletRefundedEvent event)
	{
		Wallet wallet = walletRepository.findById(event.getWalletId()).get();
		wallet.setBalance(wallet.getBalance() + event.getBalance());
		walletRepository.save(wallet);
	}
}
