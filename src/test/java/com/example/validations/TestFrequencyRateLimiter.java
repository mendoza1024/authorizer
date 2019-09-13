package com.example.validations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.example.beans.Transaction;
import com.example.commons.MessageProvider;
import com.example.processor.TransactionProcessor;


public class TestFrequencyRateLimiter {

	@Test
	@SuppressWarnings("deprecation")
	public void testFrequencyRateLimiter() {
		FrequencyRateLimiter limiter = new FrequencyRateLimiter(1000 * 60, 2,TransactionProcessor::generateKey);
		
		Transaction tx1 = new Transaction();
		tx1.setMerchant("m1");
		tx1.setAmount(1);
		tx1.setTime(new Date(2019,8,01,00,00,00));
		
		Transaction tx2 = new Transaction();
		tx2.setMerchant("m1");
		tx2.setAmount(1);
		tx2.setTime(new Date(2019,8,01,00,00,01));
		
		Transaction tx3 = new Transaction();
		tx3.setMerchant("m1");
		tx3.setAmount(1);
		tx3.setTime(new Date(2019,8,01,00,00,02));
		
		Transaction tx4 = new Transaction();
		tx4.setMerchant("m2");
		tx4.setAmount(1);
		tx4.setTime(new Date(2019,8,01,00,00,02));
		
				
		assertNull(limiter.isAllowed(null,tx1));
		assertNull(limiter.isAllowed(null,tx2));
		assertNotNull(limiter.isAllowed(null,tx3));
		assertEquals(MessageProvider.getInstance().getDoubledTransaction(), limiter.isAllowed(null,tx3));
		assertNull(limiter.isAllowed(null,tx4));

	}
	
}
