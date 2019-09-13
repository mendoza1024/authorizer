package com.example.validations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.example.beans.Transaction;
import com.example.commons.MessageProvider;


public class TestTimeRateLimiter {

	@Test
	@SuppressWarnings("deprecation")
	public void testTimeRateLimiter() {
		TimeRateLimiter limiter = new TimeRateLimiter(1000 * 60, 2);
		
		Transaction tx1 = new Transaction();
		tx1.setTime(new Date(2019,8,01,00,00,00));
		
		Transaction tx2 = new Transaction();
		tx2.setTime(new Date(2019,8,01,00,00,01));
		
		Transaction tx3 = new Transaction();
		tx3.setTime(new Date(2019,8,01,00,00,02));
		
		Transaction tx4 = new Transaction();
		tx4.setTime(new Date(2019,8,01,00,01,01));

		Transaction tx5 = new Transaction();
		tx5.setTime(new Date(2019,8,01,00,01,02));
		
		Transaction tx6 = new Transaction();
		tx6.setTime(new Date(2019,8,01,00,01,03));
		
		assertNull(limiter.isAllowed(null,tx1));
		assertNull(limiter.isAllowed(null,tx2));
		assertNotNull(limiter.isAllowed(null,tx3));
		
		assertNull(limiter.isAllowed(null,tx4));
		assertNull(limiter.isAllowed(null,tx5));
		assertEquals(MessageProvider.getInstance().getHighFrequency(), limiter.isAllowed(null,tx6));
	}
	
}
