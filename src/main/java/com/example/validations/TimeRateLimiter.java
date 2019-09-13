package com.example.validations;

import java.util.TreeMap;

import com.example.beans.Account;
import com.example.beans.Transaction;
import com.example.commons.MessageProvider;

public class TimeRateLimiter implements Validator<Account, Transaction>{

	protected final long TIME_SPAN;  

	protected final long MAX;

	protected final TreeMap<Long, Integer> state = new TreeMap<>();

	public TimeRateLimiter(long timeSpan, int max) {
		this.TIME_SPAN = timeSpan;
		this.MAX = max;
		state.put(Long.MAX_VALUE, 0);
		state.put(0l, 0);
	}

	public String isAllowed(Account ac, Transaction tx) {
		String result = null;
		
		boolean allowed = false;
		if(tx == null) {
			return result;
		}
		
		long currentTime = tx.getTime().getTime();

		long ceil = state.ceilingKey(currentTime);
		long floor = state.floorKey(currentTime);

		long ceilDiff = ceil - currentTime;
		long floorDiff = currentTime - floor;	

		
		if(ceilDiff > TIME_SPAN && TIME_SPAN < floorDiff ) {
			allowed = createBucket(state, currentTime);
		}  else if (ceilDiff <= TIME_SPAN ) {
			allowed = pushInBucket(state, ceil);
		} else if (floorDiff <= TIME_SPAN ){
			allowed = pushInBucket(state, floor);
		} else {
			allowed = createBucket(state, currentTime);
		}

		return allowed ? null:  MessageProvider.getInstance().getHighFrequency();
	}

	private boolean createBucket(TreeMap<Long, Integer> state, long value) {
		state.put(value, 1);
		return true;
	}
	
	private boolean pushInBucket(TreeMap<Long, Integer> state, long value) {
		boolean result = false;
		Integer count = state.get(value);
		count = count == null ? 0 : count;
		if(count < MAX ) {
			state.put(value, ++count);
			result = true;
		}
		return result;
	}
}
