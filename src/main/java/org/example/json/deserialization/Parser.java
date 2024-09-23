package org.example.json.deserialization;

import org.example.json.utils.Tokens;
import org.example.json.utils.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String json;
    private int index;

    public Parser(String string, int index) {
        this.json = string.replace(" ","");
        this.index = index;
    }

    Map<String, Object> stringToMap() {
        Map<String, Object> map = new HashMap<>();
        Utils.cleanString(json);

        while(index < json.length() && json.charAt(index) != Tokens.END_OBJECT.getToken()) {
            String key = parseKey();
            skip(Tokens.COLON);
            Object value = parseValue();
            map.put(key, value);

            if (index < json.length() && json.charAt(index) == Tokens.COMMA.getToken()) {
                skip(Tokens.COMMA);
            }
        }
        skip(Tokens.END_OBJECT);
        return map;
    }

    public String parseKey() {
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        matcher.region(index, json.length());

        if (matcher.find()) {
            index = matcher.end();
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Key not found in the given JSON string");
        }
    }

    public Object parseValue() {
        char currentChar = json.charAt(index);

        if (currentChar == Tokens.START_OBJECT.getToken()) {
            skip(Tokens.START_OBJECT);
            return stringToMap();
        } else if (currentChar == Tokens.START_ARRAY.getToken()) {
            skip(Tokens.START_ARRAY);
            return parseNextArray();
        } else {
            return Utils.cleanValue(parseNextPrimitive().toString());
        }
    }

    private Object parseNextPrimitive() {
        boolean inQuotes = false;
        int startIndex = index;
        for (; index < json.length()-1; index++){
            char currentChar = json.charAt(index);
            if (currentChar == Tokens.QUOTES.getToken()) {
                inQuotes =! inQuotes;
            }
            if ((!inQuotes && (currentChar == Tokens.COMMA.getToken() ||
                    currentChar == Tokens.END_OBJECT.getToken() ||
                    currentChar == Tokens.END_ARRAY.getToken())) ||
                    index == json.length() - 1) {   return json.substring(startIndex, index);
            }
        }
        return null;
    }

    public List<Object> parseNextArray() {
        List<Object> array = new ArrayList<>();
        while (index < json.length() && json.charAt(index) != Tokens.END_ARRAY.getToken()) {
            array.add(parseValue());
            if (json.charAt(index) == Tokens.COMMA.getToken() ) {
                skip(Tokens.COMMA);
            }
        }
        skip(Tokens.END_ARRAY);
        return array;
    }

    // принимаем параметр только для того, чтобы было ясно, что скипаем
    private void skip(Tokens token){
            index++;
    }

    public void resetIndex() {
        index = 0;
    }
}
