package com.example.demo;

import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CarTest {


    /**
     *
     */
    @Test
    void test() {
        var jacksonModule = new JacksonModule(JacksonOption.RESPECT_JSONPROPERTY_ORDER, JacksonOption.RESPECT_JSONPROPERTY_REQUIRED);
        var jakartaModule = new JakartaValidationModule(JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS);

        var configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        var config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
            .without(Option.FLATTENED_ENUMS_FROM_TOSTRING)
            .without(Option.SCHEMA_VERSION_INDICATOR)
            .with(jacksonModule)
            .with(jakartaModule)
            .build();

        var generator = new SchemaGenerator(config);
        var jsonSchema = generator.generateSchema(Car.class);

        System.out.println(jsonSchema.toPrettyString());
    }
}