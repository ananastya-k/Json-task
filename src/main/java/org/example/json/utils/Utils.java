package org.example.json.utils;

public class Utils {
    public static boolean isObjectValue(String value) {
        return value.startsWith("{") && value.endsWith("}")
                || value.startsWith("[") && value.endsWith("]");
    }

    public static void cleanString(String string){
        string.replaceAll(" ", "");

    }
    public static String cleanValue(String string){
        string = string.trim().replaceAll("^\"|\"$", "");
        string = string.trim().replaceAll("^\\[|\\]$", "");
        return string;
    }
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz)
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz.isPrimitive();
    }


}
