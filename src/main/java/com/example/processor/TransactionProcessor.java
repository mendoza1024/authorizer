package com.example.processor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import com.example.beans.Account;
import com.example.beans.Operation;
import com.example.beans.Transaction;
import com.example.commons.MessageProvider;
import com.example.configs.AppConfigs;
import com.example.validations.CommonTransactionValidations;
import com.example.validations.FrequencyRateLimiter;
import com.example.validations.TimeRateLimiter;
import com.example.validations.Validator;

public class TransactionProcessor implements Processor<List<Operation>, List<Account>>{

	
	@SuppressWarnings("unchecked")
	public List<Account> process(List<Operation> operations) {
		List<Account> result = new LinkedList<>();
		
		//Rate Limiter validation
		TimeRateLimiter rateLimiter = new TimeRateLimiter(AppConfigs.getTransactionTimeSpan(), AppConfigs.getTransactionMax());
		//Frequency Limiter validation
		FrequencyRateLimiter freqRateLimiter = new FrequencyRateLimiter(AppConfigs.getFrequentTimeSpan(), AppConfigs.getFrequentMaxTransactions(),TransactionProcessor::generateKey);  


		Account currentAccount = null;
		for(Operation element:operations) {
			if(isTransaction(element)) {
				currentAccount = currentAccount.clone();
				Transaction tx = element.getTransaction();
				
				String[] rules = runValidations(currentAccount, tx, CommonTransactionValidations::validateBalance,CommonTransactionValidations::validateActiveCard, rateLimiter, freqRateLimiter);
				if(rules == null || rules.length == 0) {
					currentAccount.setAvailableLimit(currentAccount.getAvailableLimit() - tx.getAmount());
				} else {
					currentAccount.setViolations(rules);
				}
			} else if(isAccount(element)) {
				if(currentAccount != null) {
					currentAccount = currentAccount.clone();
					currentAccount.setViolations(new String[] {MessageProvider.getInstance().getAccountAlreadyInitialized()});
				} else {
					currentAccount = element.getAccount().clone();
				}
			}	
			result.add(currentAccount);
		};
		return result;
	}

	
	
	public static String generateKey(Transaction tx) {
		return tx.getMerchant() + File.pathSeparator + tx.getAmount();
	}
	
	/**
	 * Run validations over a transaction and return a descriptive message if the transaction failed validation.
	 * 
	 * @param tx
	 * @param validators
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String[] runValidations(Account ac, Transaction tx, Validator<Account, Transaction> ... validators) {
		List<String> results = new LinkedList<>();
		if(validators == null || validators.length == 0) {
			return results.toArray(new String[] {});
		}
		for(Validator<Account, Transaction> validator: validators) {
			String msg = validator.isAllowed(ac, tx);
			if(msg != null) {
				results.add(msg);
			}
		}
		return results.toArray(new String[] {});
	}

	/**
	 * Run a set of rules over a transaction and return descriptive message for those where the rules 
	 * failed and null when validation is ok. 
	 * @param account
	 * @param transaction
	 * @param rules
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String[] runRules(Account account,Transaction transaction, BiFunction<Account, Transaction, String> ... rules) {
		List<String> results = new LinkedList<>();
		if(rules == null || rules.length == 0) {
			return results.toArray(new String[] {});
		}
		for(BiFunction<Account, Transaction, String> rule: rules) {
			String result = rule.apply(account, transaction);
			if(result != null) {
				results.add(result);
			}
		}
		return results.toArray(new String[] {});
	}

	/**
	 * Checks if the operation is an Account.
	 * @param item
	 * @return
	 */
	protected boolean isAccount(Operation item) {
		if(item != null && item.getAccount() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the operation is a Transaction.
	 * @param item
	 * @return
	 */
	protected boolean isTransaction(Operation item) {
		if(item != null && item.getTransaction() != null) {
			return true;
		}
		return false;
	}

}
