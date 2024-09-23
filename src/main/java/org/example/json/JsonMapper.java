package org.example.json;

import org.example.json.deserialization.Deserializer;
import org.example.json.serialization.Serializer;

public class JsonMapper {
    private final Serializer cgen = new Serializer();

    private final Deserializer deserializer = new Deserializer();
    public String writeAsJsonString(Object object) {
        return object == null ? "null" : cgen.serialize(object);
    }

    public <T> T writeJsonAsObject(String json, Class<T> clazz) {
        return (json == null || clazz == null) ? null : deserializer.fromJson(json,clazz);
    }


}
