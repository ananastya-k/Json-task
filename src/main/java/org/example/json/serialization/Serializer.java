package org.example.json.serialization;

import org.example.json.exceptions.JsonSerializationException;
import org.example.json.utils.ContextTokenType;
import org.example.json.utils.Utils;

import java.lang.reflect.Field;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Serializer {

    public String serialize(Object obj) {
        JsonContext context = generateContext(obj);
        return context.toJsonString();
    }

    public JsonContext generateContext(Object obj) {
        JsonContext rootContext = new JsonContext();
        buildContext(rootContext, obj);

        return rootContext;
    }

    private void buildContext(JsonContext parentContext, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                processingField(parentContext, field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e){
            throw JsonSerializationException.failedBuild(e.getMessage());
        }

    }

    private void processingField(JsonContext parentContext, String name, Object value) {

            JsonContext childContext = new JsonContext(name);
            parentContext.addChild(childContext);
            processingValue(value, childContext);

    }

    private void processingValue(Object value, JsonContext childContext) {
        if (value == null) {
            childContext.setTokens(ContextTokenType.PRIMITIVE);
            childContext.setValue("null");
            return;
        }

        Class<?> clazz = value.getClass();

        if (value instanceof String || clazz == Character.class) {
            childContext.setTokens(ContextTokenType.STRING);
            childContext.setValue(value.toString());
        } else if ( Utils.isPrimitiveOrWrapper(clazz)) {
            childContext.setTokens(ContextTokenType.PRIMITIVE);
            childContext.setValue(value.toString());
        }else if (clazz.isArray()){
            childContext.setTokens(ContextTokenType.ARRAY);
            generateArrayContext(childContext, value);
        } else if( value instanceof List<?>) {
            childContext.setTokens(ContextTokenType.ARRAY);
            generateListContext(childContext, (List<?>) value);
        } else if (value instanceof Map<?,?>) {
            childContext.setTokens(ContextTokenType.OBJECT);
            generateMapContext(childContext, (Map<?,?>) value);
        } else if (value instanceof UUID) {
            childContext.setTokens(ContextTokenType.STRING);
            childContext.setValue(value.toString());
        } else if (value instanceof Temporal) {
            childContext.setTokens(ContextTokenType.STRING);
            childContext.setValue(value.toString());
        }else {
            childContext.setTokens(ContextTokenType.OBJECT);
            buildContext(childContext, value);
        }
    }

    private void generateArrayContext(JsonContext parentContext, Object array) {
        int length = java.lang.reflect.Array.getLength(array);

        for (int i = 0; i < length; i++) {
            Object item = java.lang.reflect.Array.get(array, i);
            processingField(parentContext, null, item);
        }
    }
    private void generateListContext(JsonContext parentContext, List<?> fieldValue) {
        for (Object object : fieldValue) {
            processingField(parentContext, null, object);
        }
    }

    private void generateMapContext(JsonContext parentContext, Map<?,?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            processingField(parentContext, entry.getKey().toString(), entry.getValue());
        }
    }
}
