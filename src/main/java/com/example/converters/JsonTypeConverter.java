package com.example.converters;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Specific Json implementation of converter.
 *
 * @param <T>
 */
public class JsonTypeConverter<T> implements Converter<T>{

	private final static Logger LOG = Logger.getLogger(JsonTypeConverter.class.getName());

	
	private final ObjectMapper objectMapper = new ObjectMapper();


	@Override
	public T toObject(String obj, Class<T> clazz) {
		 try {
			return  objectMapper.readValue(obj, clazz);
		} catch (IOException e) {
			LOG.warning(e.getMessage());
		}
		 return null;
	}

	@Override
	public String toString(T input) {
		try {
			return objectMapper.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			LOG.warning(e.getMessage());
		}
		return null;
	}

	@Override
	public List<T> toObjectList(List<String> inputs, Class<T> clazz) {
		return inputs.stream().map(line -> {
			return toObject(line, clazz);
		})
		.collect(Collectors.toList());
	}

	@Override
	public List<String> toStringList(List<T> inputs) {
		return inputs.stream().map(obj -> {
			return toString(obj);
		}).collect(Collectors.toList());
	}


}
