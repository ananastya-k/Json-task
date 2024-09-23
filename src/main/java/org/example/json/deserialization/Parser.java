package org.example.json.deserialization;

import jdk.jshell.execution.Util;
import org.example.json.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    //regex - не самая удачная идея. позже заменю на мини-парсер
    private final String REGEX = "\"([A-Za-z0-9_]+)\":((\\{.*?})|(\\[.*?])|(\".*?\")|(\\d+|true|false|null)),?";

    Object parseArray(String value, Class<?> componentType) {
        Utils.cleanString(value);
        String[] elements = value.split(",");
        Object array = Array.newInstance(componentType, elements.length);

        for (int i = 0; i < elements.length; i++) {
            Array.set(array, i, parsePrimitive(elements[i].trim(), componentType));
        }

        return array;
    }

    Collection<?> parseList(Object value, Class<? extends Collection> collectionType) {
        Collection<Object> list = getListType(collectionType);

        if (value instanceof Collection) {
            list.addAll((Collection<?>) value);
        } else {
            String[] elements = Utils.cleanValue(value.toString()).split(",");
            for (String element : elements) {
                list.add(parsePrimitive(element.trim(), Object.class));
            }
        }

        return list;
    }

    private static Collection<Object> getListType(Class<? extends Collection> collectionType) {
        Collection<Object> list;
        if (List.class.isAssignableFrom(collectionType)) {
            list = new ArrayList<>();
        } else if (Set.class.isAssignableFrom(collectionType)) {
            list = new HashSet<>();
        } else {
            throw new IllegalArgumentException("Unsupported collection type: " + collectionType);
        }
        return list;
    }

    Map<String, Object> parseMap(String json) {
        Map<String, Object> map = new HashMap<>();

        Utils.cleanString(json);

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2).trim();
            if (Utils.isObjectValue(value)) {
                value = value.substring(1, value.length() - 1);
                map.put(key, parseMap(value));
            } else {
                map.put(key, Utils.cleanValue(value));
            }
        }
        return map;
    }
    Object parsePrimitive(String value, Class<?> clazz) {
        if (clazz == int.class || clazz == Integer.class) {
            return Integer.parseInt(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return Long.parseLong(value);
        } else if (clazz == double.class || clazz == Double.class) {
            return Double.parseDouble(value);
        } else if (clazz == float.class || clazz == Float.class) {
            return Float.parseFloat(value);
        } else if (clazz == short.class || clazz == Short.class) {
            return Short.parseShort(value);
        } else if (clazz == byte.class || clazz == Byte.class) {
            return Byte.parseByte(value);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (clazz == char.class || clazz == Character.class) {
            return value.charAt(0);
        }
        return null;
    }
}
