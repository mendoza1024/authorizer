package com.example.validations;

import com.example.beans.Account;
import com.example.beans.Transaction;
import com.example.commons.MessageProvider;

public class CommonTransactionValidations {

	public static String validateActiveCard(Account account, Transaction tx) {
		String result = null;
		if(!account.getActiveCard()) {
			return MessageProvider.getInstance().getCardBlocked();
		}
		return result;
	}
	
	public static String validateBalance(Account account, Transaction tx) {
		String result = null;
		if(tx.getAmount() > account.getAvailableLimit()) {
			return MessageProvider.getInstance().getInsufficientLimit();
		}
		return result;
	}
}
