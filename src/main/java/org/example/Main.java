package org.example;

import org.example.json.JsonMapper;
import org.example.model.Baggage;
import org.example.model.CollectionContainer;
import org.example.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Person person = new Person(
                "Alise",
                "Sur",
                19,
                new Baggage(
                10.5f,
                'A',
                true,
                "Hello, World!",
                new Object(),
                new double[]{1.1, 2.2, 3.3},
                new boolean[]{true, false, true},
                new String[]{"One", "Two", "Three"},
                new Integer[][]{{1, 2}, {3}},
                List.of("First", "Second", "Third"),
                Map.of(1, "One", 2, "Two", 3, "Three"),
                new ArrayList[10],
                new Baggage.CustomClass(),
                new Baggage.CustomClass[]{
                        new Baggage.CustomClass(),
                        new Baggage.CustomClass()
                }
        ));

        JsonMapper jsonMapper = new JsonMapper();

        String jsonString = jsonMapper.writeAsJsonString(person);
        System.out.println(jsonString);

        Person deserializePerson = jsonMapper.writeJsonAsObject(jsonString, Person.class);
        System.out.println(deserializePerson.toString());

    }
}

