package org.example.json.deserialization;

import org.example.json.exceptions.JsonDeserializationException;
import org.example.json.utils.Utils;

import java.lang.reflect.Field;
import java.util.*;

public class Deserializer {

    private final Parser parser = new Parser();
    public <T> T fromJson(String json, Class<T> clazz) {
        Map<String, Object> map = parser.parseMap(json);
        return fillFields(map, clazz);
    }

    private <T> T fillFields(Map<String, Object> map, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = map.get(field.getName());
                if (value != null) {
                    Object convertedValue = convertValue(value, field.getType());
                    field.set(instance, convertedValue);
                }
            }
            return instance;
        } catch (Exception e) {
            throw JsonDeserializationException.failedBuild(e.getMessage());
        }
    }

    private Object convertValue(Object value, Class<?> clazz) {
        if (clazz.isArray()) {
            return parser.parseArray(value.toString(), clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return parser.parseList(value, (Class<? extends Collection>) clazz);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return value;
        } else if (Utils.isPrimitiveOrWrapper(clazz)) {
            return parser.parsePrimitive(value.toString(), clazz);
        } else if (clazz == String.class) {
            return value.toString();
        } else {
            return fromJson(value.toString(), clazz);
        }
    }
}
