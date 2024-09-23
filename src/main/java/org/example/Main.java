package org.example;

import org.example.json.JsonMapper;
import org.example.model.CollectionContainer;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), new ArrayList<Order>() {{
            add(new Order(UUID.randomUUID(), new ArrayList<Product>() {{
                add(new Product(UUID.randomUUID(), "Product 1", 99.99, new HashMap<UUID, BigDecimal>() {{
                    put(UUID.randomUUID(), BigDecimal.valueOf(99.99));
                }}));
                add(new Product(UUID.randomUUID(), "Product 2", 49.99, new HashMap<UUID, BigDecimal>() {{
                    put(UUID.randomUUID(), BigDecimal.valueOf(49.99));
                }}));
            }} , OffsetDateTime.now()));
        }});

        JsonMapper jsonMapper = new JsonMapper();

        String jsonString = jsonMapper.writeAsJsonString(customer);
        System.out.println(jsonString);

        Customer deserializePerson = jsonMapper.writeJsonAsObject(jsonString, Customer.class);

        System.out.println(deserializePerson.toString());


    }
}

