package org.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class Baggage {

    // Примитивные типы
    public float floatField = 10.5f;
    public char charField = 'A';

    // Классы-обертки для примитивов
    public Boolean booleanWrapper = true;

    // Ссылочные типы
    public String strField = "Hello, World!";
    public Object objField = new Object();

    // Массивы примитивных типов
    public double[] doubleArrayField = {1.1, 2.2, 3.3};
    public boolean[] booleanArrayField = {true, false, true};

    // Массивы объектных типов
    public String[] stringArrayField = {"One", "Two", "Three"};
    public Integer[][] integerArrayField = {{1, 2}, {3}};

    // Обобщенные типы
    public List<String> stringList = new ArrayList<>(List.of("First", "Second", "Third"));
    public Map<Integer, String> integerStringMap = new HashMap<>(Map.of(1, "One", 2, "Two", 3, "Three"));

    // Массивы обобщенных типов
    public List<String>[] genericArrayField = new ArrayList[10];

    // Пользовательский класс
    public static class CustomClass {
        public String name = "CustomName";

        @Override
        public String toString() {
            return "CustomClass{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
    public CustomClass customField = new CustomClass();

    // Массивы пользовательских классов
    public CustomClass[] customClassArray = {new CustomClass(), new CustomClass()};

    // Поле времени
  //  public LocalDateTime dateTimeField = LocalDateTime.now();

    @Override
    public String toString() {
        return "AllTypesClass{" +
                "floatField=" + floatField +
                ", charField=" + charField +
                ", booleanWrapper=" + booleanWrapper +
                ", strField='" + strField + '\'' +
                ", objField=" + objField +
                ", doubleArrayField=" + Arrays.toString(doubleArrayField) +
                ", booleanArrayField=" + Arrays.toString(booleanArrayField) +
                ", stringArrayField=" + Arrays.toString(stringArrayField) +
                ", integerArrayField=" + Arrays.toString(integerArrayField) +
                ", stringList=" + stringList +
                ", integerStringMap=" + integerStringMap +
                ", genericArrayField=" + Arrays.toString(genericArrayField) +
                ", customField=" + customField.toString() +
                ", customClassArray=" + Arrays.toString(customClassArray) +
                '}';
    }
}
