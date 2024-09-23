package org.example.json.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContextTokenType {
    ARRAY("[","]"),
    OBJECT("{","}"),
    PRIMITIVE("",""),
    STRING("\"","\""),
    NAME("\"","\":");

    private final String startToken;
    private final String endToken;


}
