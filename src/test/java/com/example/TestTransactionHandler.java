package com.example;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestTransactionHandler {

	@Test
	public void testProcessOperations() {
		TransactionHandler txh = new TransactionHandler();
		List<String> lines = readFile("./resources/operations");

		List<String> result = txh.handleOperations(lines);
		assertEquals("{\"activeCard\":true,\"availableLimit\":100,\"violations\":[]}", result.get(0));
		assertEquals("{\"activeCard\":true,\"availableLimit\":80,\"violations\":[]}", result.get(1));
		assertEquals("{\"activeCard\":true,\"availableLimit\":80,\"violations\":[\"insufficient-limit\"]}",
				result.get(2));
	}

	@Test
	public void testProcessOperations_1() {
		TransactionHandler txh = new TransactionHandler();
		List<String> lines = readFile("./resources/operations_1");

		List<String> result = txh.handleOperations(lines);
		assertEquals("{\"activeCard\":true,\"availableLimit\":100,\"violations\":[]}", result.get(0));
		assertEquals("{\"activeCard\":true,\"availableLimit\":80,\"violations\":[]}", result.get(1));
		assertEquals("{\"activeCard\":true,\"availableLimit\":80,\"violations\":[\"insufficient-limit\"]}",
				result.get(2));
		assertEquals("{\"activeCard\":true,\"availableLimit\":80,\"violations\":[\"account-already-initialized\"]}",
				result.get(3));
	}
	
	@Test
	public void testProcessOperations_2() {
		TransactionHandler txh = new TransactionHandler();
		List<String> lines = readFile("./resources/operations_2");

		List<String> result = txh.handleOperations(lines);
		assertEquals("{\"activeCard\":true,\"availableLimit\":100,\"violations\":[]}", result.get(0));

		assertEquals("{\"activeCard\":true,\"availableLimit\":78,\"violations\":[\"doubled-transaction\"]}",
				result.get(4));
	}

	@Test
	public void testProcessOperations_3() {
		TransactionHandler txh = new TransactionHandler();
		List<String> lines = readFile("./resources/operations_3");

		List<String> result = txh.handleOperations(lines);
		assertEquals("{\"activeCard\":false,\"availableLimit\":100,\"violations\":[]}", result.get(0));
		assertEquals("{\"activeCard\":false,\"availableLimit\":100,\"violations\":[\"card-blocked\"]}", result.get(1));
		assertEquals("{\"activeCard\":false,\"availableLimit\":100,\"violations\":[\"account-already-initialized\"]}",
				result.get(3));
	}
	
	@Test
	public void testProcessOperations_4() {
		TransactionHandler txh = new TransactionHandler();
		List<String> lines = readFile("./resources/operations_4");

		List<String> result = txh.handleOperations(lines);
		assertEquals("{\"activeCard\":true,\"availableLimit\":100,\"violations\":[]}", result.get(0));
		assertEquals("{\"activeCard\":true,\"availableLimit\":97,\"violations\":[\"high-frequency-small-interval\"]}",
				result.get(4));
	}
	
	/**
	 * This method take a file a return an array of text lines
	 * 
	 * @param file
	 * @return
	 */
	private List<String> readFile(String file) {
		BufferedReader reader = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			List<String> lines = reader.lines().collect(Collectors.toList());
			fr.close();
			reader.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
