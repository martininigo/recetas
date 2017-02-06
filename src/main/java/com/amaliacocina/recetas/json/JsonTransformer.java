package com.amaliacocina.recetas.json;

public interface JsonTransformer {
    String toJson(Object data);
    <T> T fromJSON(String json, Class<T> clazz);
}