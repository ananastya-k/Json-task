package org.example.json.exceptions;

public class JsonSerializationException extends RuntimeException{
    private JsonSerializationException(String message){
        super(message);
    }
    public static JsonSerializationException failedBuild(String message){
        return new JsonSerializationException(message);
    }
}
