package com.example.processor;


public interface Processor<T,K> {
	
	/**
	 * The purpose of this interface is to provide an standard method to process transactions.
	 * 
	 * @param t provide the input transaction. 
	 * @return the processed transaction
	 */
	K process(T t);
}
