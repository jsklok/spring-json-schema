package com.example.demo.system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public abstract class JsonSchemaValidator {
    // -----------------------------------------------------------------------------------------------------------------

    //
    private static final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

    /**
     *
     * @param schema
     * @param input
     * @return
     */
    public static Set<ValidationMessage> validate(String schema, Map<String, Object> input) {
        var jsonSchema = jsonSchemaFactory.getSchema(schema);
        var jsonNode = new ObjectMapper().valueToTree(input);
        return jsonSchema.validate(jsonNode);
    }

}
