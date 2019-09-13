package com.example.configs;

/**
 * This class is an abstraction of the configuration so this is spread everywhere.
 *
 */
public class AppConfigs {

	public static Long getTransactionTimeSpan() {
		return 1000 * 60 * 2l;
	}
	
	public static Integer getTransactionMax() {
		return 3;
	}
	
	public static Long getFrequentTimeSpan() {
		return 1000 * 60 * 2l;
	}
	
	public static Integer getFrequentMaxTransactions() {
		return 2;
	}
	
}
