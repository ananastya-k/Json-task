package org.example.json.exceptions;

public class JsonDeserializationException extends RuntimeException{
    private JsonDeserializationException(String message){
        super(message);
    }
    public static JsonDeserializationException failedBuild(String message){
        return new JsonDeserializationException(message);
    }
}
