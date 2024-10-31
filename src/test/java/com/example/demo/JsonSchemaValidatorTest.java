package com.example.demo;

import com.example.demo.system.utils.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class JsonSchemaValidatorTest {

    /**
     *
     */
    @BeforeEach
    void setup() {

    }

    /**
     *
     */
    @Test
    void whenRequiredIsGiven_thenNoAssertions() {
        var schema = """
            {
              "type": "object",
              "properties": {
                "first_name": {
                  "type": "string"
                },
                "last_name": {
                  "type": "string"
                }
              },
              "required": [
                "first_name",
                "last_name"
              ]
            }
            """;

        //
        var assertions = JsonSchemaValidator.validate(schema, Map.of(
            "first_name", "John",
            "last_name", "Doe"
        ));

        assertEquals(0, assertions.size());
    }

    /**
     *
     */
    @Test
    void whenRequiredNotGiven_thenAssertions() {
        var schema = """
            {
              "type": "object",
              "properties": {
                "first_name": {
                  "type": "string"
                },
                "last_name": {
                  "type": "string"
                }
              },
              "required": [
                "first_name",
                "last_name"
              ]
            }
            """;

        //
        var assertions = JsonSchemaValidator.validate(schema, Map.of(
            "first_name", "John"
        ));

        assertEquals(1, assertions.size());
    }


    /**
     *
     */
    @Test
    void whenRequired_thenAssertions() {
        var schema = """
            {
              "type": "object",
              "properties": {
                "auth": {
                  "type": "object",
                  "properties": {
                    "root_user": {
                      "type": "string"
                    },
                    "root_password": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "root_user",
                    "root_password"
                  ]
                }
              }
            }
            """;

        //
        var assertions = JsonSchemaValidator.validate(schema, Map.of(
            "auth", Map.of(
                "root_user", "root"
            )
        ));

        assertEquals(1, assertions.size());
    }


    /**
     *
     */
    @Disabled
    @Test
    void whenRequiredNested_thenAssertions() {
        var schema = """
            {
              "type": "object",
              "properties": {
                "auth": {
                  "type": "object",
                  "properties": {
                    "root_user": {
                      "type": "string"
                    },
                    "root_password": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "root_user",
                    "root_password"
                  ]
                }
              },
              "required": [
                "auth"
              ]
            }
            """;

        //
        var assertions = JsonSchemaValidator.validate(schema, Map.of(
        ));

        assertEquals(2, assertions.size());
    }


    /**
     *
     */
    @Test
    void whenIfElse_thenAssertions() {
        var schema = """
            {
               "type": "object",
               "properties": {
                 "animal": {
                   "enum": [
                     "Cat",
                     "Fish"
                   ]
                 }
               },
               "allOf": [
                 {
                   "if": {
                     "properties": {
                       "animal": {
                         "const": "Cat"
                       }
                     }
                   },
                   "then": {
                     "properties": {
                       "food": {
                         "type": "string",
                         "enum": [
                           "meat",
                           "grass",
                           "fish"
                         ]
                       }
                     },
                     "required": [
                       "food"
                     ]
                   }
                 },
                 {
                   "if": {
                     "properties": {
                       "animal": {
                         "const": "Fish"
                       }
                     }
                   },
                   "then": {
                     "properties": {
                       "food": {
                         "type": "string",
                         "enum": [
                           "insect",
                           "worms"
                         ]
                       },
                       "water": {
                         "type": "string",
                         "enum": [
                           "lake",
                           "sea"
                         ]
                       }
                     },
                     "required": [
                       "food",
                       "water"
                     ]
                   }
                 },
                 {
                   "required": [
                     "animal"
                   ]
                 }
               ]
             }
            """;

        //
        var assertions = JsonSchemaValidator.validate(schema, Map.of(
            "animal", "Cat",
            "food", "meat"
        ));

        assertEquals(0, assertions.size());
    }


}