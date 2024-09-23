package org.example.json;

import org.example.json.deserialization.Deserializer;
import org.example.json.serialization.Serializer;

public class JsonMapper {
    private final Serializer cgen = new Serializer();

    public String writeAsJsonString(Object object) throws IllegalAccessException {
        if(object == null) {
            return "null";
        }
        return  cgen.serialize(object);
    }

    public <T> T writeJsonAsObject(String json, Class<T> clazz) throws IllegalAccessException {
        if (json == null || clazz == null) {
            return null;
        }
        Deserializer deserializer = new Deserializer();
        return deserializer.fromJson(json,clazz);
    }


}
