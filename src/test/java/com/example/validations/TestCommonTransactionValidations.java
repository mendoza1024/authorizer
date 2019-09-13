package com.example.validations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.example.beans.Account;
import com.example.beans.Transaction;
import com.example.commons.MessageProvider;


public class TestCommonTransactionValidations {

	
	@Test
	public void testValidateActiveCard() {
		Account account = new Account();
		account.setActiveCard(false);
		assertEquals(MessageProvider.getInstance().getCardBlocked(),CommonTransactionValidations.validateActiveCard(account, null));
		account.setActiveCard(true);
		assertNull(CommonTransactionValidations.validateActiveCard(account, null));
	}
	
	@Test
	public void testValidateBalance() {
		Account account = new Account();
		account.setAvailableLimit(100);
		Transaction tx = new Transaction();
		tx.setAmount(101);
		assertEquals(MessageProvider.getInstance().getInsufficientLimit(), CommonTransactionValidations.validateBalance(account, tx));
		tx.setAmount(100);
		assertNull(CommonTransactionValidations.validateBalance(account, tx));
		
		tx.setAmount(10);
		assertNull(CommonTransactionValidations.validateBalance(account, tx));
	}
	
	
}
