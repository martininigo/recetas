package com.amaliacocina.recetas.json;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonTransformerImplJackson implements JsonTransformer {

	public String toJson(Object data) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T fromJSON(String json, Class<T> clazz) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.readValue(json, clazz);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
