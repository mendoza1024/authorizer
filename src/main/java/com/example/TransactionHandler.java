package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.example.beans.Account;
import com.example.beans.Operation;
import com.example.converters.Converter;
import com.example.converters.JsonTypeConverter;
import com.example.processor.Processor;
import com.example.processor.TransactionProcessor;

public class TransactionHandler {
	
	/**
	 * Entry point to process transactions
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		List<String> lines = new LinkedList<String>();
		BufferedWriter bufferedWriter = null;
		try {
			TransactionHandler transactions = new TransactionHandler();
			//we just take the transactions from standard input.
			while (scanner.hasNextLine() ) {
				lines.add(scanner.nextLine());
			}
			//if no lines just stop
			if(lines == null || lines.size() == 0) {
				return;
			}
			
			List<String> out = transactions.handleOperations(lines);
			
			//we just send result to standard output.
			if(out != null) {
				out.stream().forEach(ln -> {System.out.println(ln);});
			}
		} finally {
			if(bufferedWriter != null ) {
				bufferedWriter.close();
			}
	        scanner.close();	
		}
		

	}
	
	/**
	 * Parse input from string to objects.
	 * @param input
	 * @return
	 */
	protected List<Operation> parseInput(List<String> input) {
		Converter<Operation> converter = new JsonTypeConverter<>();
		return converter.toObjectList(input, Operation.class);
	}
	
	/**
	 * Runs operation processing.
	 * @param inputs
	 * @return
	 */
	public List<String> handleOperations(List<String> inputs) {
		Processor<List<Operation>, List<Account>> processor = new TransactionProcessor();
		List<Operation> operations = parseInput(inputs);
		List<Account> result = processor.process(operations);
		return parseOutput(result);
	}
	/**
	 * Parse output to converted to String  
	 * @param input
	 * @return
	 */
	protected List<String> parseOutput(List<Account> input) {
		Converter<Account> converter = new JsonTypeConverter<>();
		return converter.toStringList(input);
	}
	
	
}
