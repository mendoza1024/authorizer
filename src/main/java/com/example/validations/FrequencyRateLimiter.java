package com.example.validations;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.beans.Account;
import com.example.beans.Transaction;
import com.example.commons.MessageProvider;

public class FrequencyRateLimiter implements Validator<Account, Transaction> {

	private final Function<Transaction, String> keyGenerator;
	
	private final Map<String, Integer> map = new HashMap<>();
	
	private final Map<String, TimeRateLimiter> timeLimiterMap = new HashMap<>();
	
	
	protected final TimeRateLimiter timeLimiter;
	
	protected final int MAX;
	
	protected final long TIME_SPAN;

	public FrequencyRateLimiter(long timeSpan, int max, Function<Transaction, String> keyGenerator) {
		this.keyGenerator = keyGenerator;
		this.timeLimiter = new TimeRateLimiter(timeSpan, max);
		this.MAX = max;
		this.TIME_SPAN = timeSpan;
	}

	public String isAllowed(Account ac, Transaction d) {	
		String result = null;
		if(!inLimit(ac, d)) {
			result = MessageProvider.getInstance().getDoubledTransaction();
		}
		return result;
	}
	
	private boolean inLimit(Account ac, Transaction d) {
						
		Boolean result = false;
		String key = keyGenerator.apply(d);
		Integer counter = map.get(key);
		
		TimeRateLimiter tl = timeLimiterMap.get(key);
		if(counter == null) {
			counter = 0;
			tl = new TimeRateLimiter(TIME_SPAN, MAX);
			timeLimiterMap.put(key, tl);
		}
		counter++;
		if(counter <= MAX && tl.isAllowed(ac, d) == null) {
			result = true;
			map.put(key, counter);
		}
		return result;
	}

}

