package com.example.demo.system.utils;

import com.example.demo.Car;
import com.example.demo.uischema.UISchemaGenerator;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;

import java.lang.reflect.Type;

/**
 *
 */
public class FormGenerator {

    /**
     *
     * @param type
     * @return
     */
    public static Form getForm(Type type) {
        var jsonSchema = generateJsonSchema(type);
        var uiSchema = generateUiSchema(type);
        return new Form(type.getTypeName(), jsonSchema, uiSchema);
    }


    /**
     * @param type
     * @return
     */
    private static String generateJsonSchema(Type type) {
        var jacksonModule = new JacksonModule(
            JacksonOption.RESPECT_JSONPROPERTY_ORDER,
            JacksonOption.RESPECT_JSONPROPERTY_REQUIRED);

        var jakartaModule = new JakartaValidationModule(
            JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS);

        var configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        var config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
            .without(Option.FLATTENED_ENUMS_FROM_TOSTRING)
            .without(Option.SCHEMA_VERSION_INDICATOR)
            .with(jacksonModule)
            .with(jakartaModule)
            .build();

        var generator = new SchemaGenerator(config);
        return generator.generateSchema(type).toPrettyString();
    }

    /**
     * Generate UI Schema
     *
     * @param type The type
     * @return The UI Schema
     */
    private static String generateUiSchema(Type type) {
        var generator = new UISchemaGenerator();
        return generator.generateSchema(type).toPrettyString();
    }

    /**
     * @param jsonSchema
     * @param uiSchema
     */
    public record Form(
        String type,
        String jsonSchema,
        String uiSchema
    ) {
    }
}
