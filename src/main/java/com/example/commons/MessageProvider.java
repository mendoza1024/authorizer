package com.example.commons;

public class MessageProvider {
	
	private static final MessageProvider instance = new MessageProvider();
	
	public static MessageProvider getInstance() {
		return instance;
	}

	public String getDoubledTransaction() {
		return "doubled-transaction";
	}
	
	public String getCardBlocked() {
		return "card-blocked";
	}
	
	public String getHighFrequency() {
		return "high-frequency-small-interval";
	}
	
	public String getAccountAlreadyInitialized() {
		return "account-already-initialized";
	}
	
	public String getInsufficientLimit() {
		return "insufficient-limit";
	}
	
}
