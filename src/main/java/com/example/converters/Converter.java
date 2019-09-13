package com.example.converters;

import java.util.List;

public interface Converter<T> {

	/**
	 * Transform an String into an object of the given class.
	 * @param input
	 * @param clazz
	 * @return
	 */
	T toObject(String input, Class<T> clazz);
		
	/**
	 * Transform an Object into a String representation.
	 * @param input
	 * @return
	 */
	String toString(T input);
	
	/**
	 * Transform a List of String into an a List of objects of the given class.
	 * @param inputs
	 * @param clazz
	 * @return
	 */
	List<T> toObjectList(List<String> inputs, Class<T> clazz);

	/**
	 * Transform a List of Objects into a List of String representation.
	 * @param input
	 * @return
	 */
	List<String> toStringList(List<T> inputs);
}
