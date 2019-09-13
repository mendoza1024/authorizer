package com.example.validations;

public interface Validator<T, K> {

	/**
	 * Determine if the input is according to the constraints.
	 * @param input is the value that will be used as key for the rate limiting buckets
	 * @return null if no error is found, otherwise a description string is returned.
	 */
	public String isAllowed(T t, K k);
	
}
