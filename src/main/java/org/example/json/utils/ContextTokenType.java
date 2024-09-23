package org.example.json.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContextTokenType {
    ARRAY(Tokens.START_ARRAY, Tokens.END_ARRAY),
    OBJECT(Tokens.START_OBJECT, Tokens.END_OBJECT),
    PRIMITIVE(Tokens.EMPTY, Tokens.EMPTY),
    STRING(Tokens.QUOTES, Tokens.QUOTES),
    NAME(Tokens.QUOTES, Tokens.QUOTES);

    private final Tokens startToken;
    private final Tokens endToken;

}
