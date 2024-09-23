package org.example.json.utils;

public enum Tokens {

    START_OBJECT('{'),
    END_OBJECT('}'),
    START_ARRAY('['),
    END_ARRAY(']'),
    COLON(':'),
    QUOTES('\"'),
    COMMA(','),
    EMPTY(' ');

    private final char token;

    Tokens(char token) {
        this.token = token;
    }

    public char getToken() {
        return token;
    }

    @Override
    public String toString() {
        return Character.toString(token);
    }
}
