package org.example.json.deserialization;

import org.example.json.converter.FieldConverter;
import org.example.json.exceptions.JsonDeserializationException;

import java.lang.reflect.Field;
import java.util.*;

public class Deserializer {

    private  Parser parser;
    private FieldConverter converter = new FieldConverter();
    public <T> T fromJson(String json, Class<T> clazz) {
        parser = new Parser(json, 0);

        Map<String, Object> map = parser.stringToMap();
        parser.resetIndex();
        return fillFields(map, clazz);
    }

    public  <T> T fillFields(Map<String, Object> map, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = map.get(field.getName());
                if (value != null) {
                    Object convertedValue = converter.convertValue(value, field.getType());
                    field.set(instance, convertedValue);
                }
            }
            return instance;
        } catch (Exception e) {
            throw JsonDeserializationException.failedBuild(e.getMessage());
        }
    }
}
