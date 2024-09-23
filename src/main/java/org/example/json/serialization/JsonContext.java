package org.example.json.serialization;

import lombok.Getter;
import lombok.Setter;
import org.example.json.utils.ContextTokenType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class JsonContext {

    private ContextTokenType tokens;
    private String name;
    private String value;
    private List<JsonContext> child;

    public JsonContext(Object obj) {
        this.tokens = ContextTokenType.OBJECT;
        this.child = new ArrayList<>();
    }

    public JsonContext(String name) {
        this.name = name;
        this.child = new ArrayList<>();
    }

    public void addChild(JsonContext child) {
        this.child.add(child);
    }

    public String toJsonString() {
        StringBuilder jsonBuilder = new StringBuilder();
        writeField(jsonBuilder);
        return jsonBuilder.toString();
    }

    public void writeField(StringBuilder jsonBuilder) {
        writeName(jsonBuilder);
        jsonBuilder.append(tokens.getStartToken());
        writeValue(jsonBuilder);
        jsonBuilder.append(tokens.getEndToken());
    }

    public void writeName(StringBuilder jsonBuilder) {
        if (name != null) {
            jsonBuilder.append(ContextTokenType.NAME.getStartToken());
            jsonBuilder.append(name);
            jsonBuilder.append(ContextTokenType.NAME.getEndToken());
        }
    }

    public void writeValue(StringBuilder jsonBuilder) {
        if (value != null) {
            jsonBuilder.append(value);
        } else {
            boolean first = true;
            for (JsonContext child : child) {
                if(!first) jsonBuilder.append(",");
                child.writeField(jsonBuilder);
                first = false;
            }
        }
    }

}
