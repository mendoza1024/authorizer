package com.example.converters;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.example.beans.Operation;

public class TestTransactionProcessor {

	
	@Test
	public void testToObject() {
		Converter<Operation> converter = new JsonTypeConverter<>();
		Operation op = converter.toObject("{ \"transaction\": { \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\" }}", Operation.class);
		assertEquals("Burger King", op.getTransaction().getMerchant());
		assertEquals(Integer.valueOf(20), op.getTransaction().getAmount());
	}
	
	
	@Test
	public void testToString() {
		Converter<Operation> converter = new JsonTypeConverter<>();
		String jsonStr = "{\"transaction\":{\"merchant\":\"Burger King\",\"amount\":20,\"time\":1550052000000}}";
		Operation op = converter.toObject(jsonStr, Operation.class);
		String json = converter.toString(op);
		assertEquals(jsonStr, json);
	}
	
	
	@Test
	public void testToObjectList() {
		Converter<Operation> converter = new JsonTypeConverter<>();
		List<String> lines = new LinkedList<>();
		lines.add("{ \"transaction\": { \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\" }}");
		List<Operation> op = converter.toObjectList(lines, Operation.class);
		assertEquals("Burger King", op.get(0).getTransaction().getMerchant());
		assertEquals(Integer.valueOf(20), op.get(0).getTransaction().getAmount());
	}
	
	@Test
	public void testToStringList() {
		Converter<Operation> converter = new JsonTypeConverter<>();
		String jsonStr = "{\"transaction\":{\"merchant\":\"Burger King\",\"amount\":20,\"time\":1550052000000}}";
		Operation op = converter.toObject(jsonStr, Operation.class);
		List<Operation> opList = new LinkedList<>();
		opList.add(op);
		List<String> json = converter.toStringList(opList);
		assertEquals(jsonStr, json.get(0));
	}
}
