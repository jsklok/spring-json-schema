package com.example.demo.uischema;

import com.example.demo.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class UISchemaGeneratorTest {

    /**
     *
     */
    @Test
    void when_then() {

        var generator = new UISchemaGenerator();
        var jsonSchema = generator.generateSchema(Car.class);
        System.out.println(jsonSchema.toPrettyString());

    }

}