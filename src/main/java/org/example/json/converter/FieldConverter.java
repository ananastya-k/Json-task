package org.example.json.converter;

import org.example.json.deserialization.Deserializer;
import org.example.json.utils.Utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FieldConverter {

    public Collection<?> toList(Object value, Class<? extends Collection> collectionType) {
        Collection<Object> list = getListType(collectionType);
        if (value instanceof Collection) {
            list.addAll((Collection<?>) value);
        } else {
            String[] elements = Utils.cleanValue(value.toString()).split(",");
            for (String element : elements) {
                list.add(convertValue(element.trim(), Object.class));
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

    public Object toArray(List<Object> value, Class<?> componentType) {
        Object array = Array.newInstance(componentType, value.size());
        for (int i = 0; i < value.size(); i++) {
            Array.set(array, i, convertValue(value.get(i), componentType));
        }

        return array;
    }

    public Object convertValue(Object value, Class<?> clazz) {
        if(value.toString().equals("null")){
            return null;
        }else if (clazz.isArray()) {return toArray((List<Object>) value, clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return toList(value, (Class<? extends Collection>) clazz);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return value;
        } else if (Utils.isPrimitiveOrWrapper(clazz)) {
            return toPrimitive(value.toString(), clazz);
        } else if (clazz == String.class) {
            return value.toString();
        } else if (clazz == UUID.class) {
            return UUID.fromString(value.toString());
        }else if (Temporal.class.isAssignableFrom(clazz)) {
            return toTemporal(value.toString(), clazz);
        }else {
            return new Deserializer().fillFields((Map<String, Object>) value,clazz);
        }
    }

    private Object toTemporal(String value, Class<?> clazz) {
        if (clazz == LocalDate.class) {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } else if (clazz == LocalDateTime.class) {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (clazz == OffsetDateTime.class) {
            return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } else if (clazz == ZonedDateTime.class) {
            return ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }
        throw new UnsupportedOperationException("Unsupported temporal type: " + clazz);

    }

    public Object toPrimitive(String value, Class<?> clazz) {
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
        } else if (clazz == BigDecimal.class) {
            return BigDecimal.valueOf(Double.parseDouble(value));
        }
        return null;
    }

}
