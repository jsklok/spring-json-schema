package com.example.demo.uischema;

import com.example.demo.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Generate UI schema for the given target type.
 */
public class UISchemaGenerator {

    /**
     * Generate UI schema for the given target type.
     *
     * @param targetType the target type for which the schema is generated
     * @return the generated ObjectNode representing the UI schema
     */
    public ObjectNode generateSchema(Type targetType) {
        return generateObjectNode(targetType);
    }

    /**
     * Generate an ObjectNode for the given target type.
     *
     * @param targetType the target type for which the ObjectNode is generated
     * @return the generated ObjectNode
     */
    private ObjectNode generateObjectNode(Type targetType) {
        var schemaNode = JsonNodeFactory.instance.objectNode();
        Class<?> clazz = (Class<?>) targetType;
        for (var field : clazz.getDeclaredFields()) {
            // Check if the field has the UISchema annotation
            if (field.isAnnotationPresent(UISchema.class)) {
                var annotation = field.getAnnotation(UISchema.class);
                // Generate field schema and add it to the schemaNode
                generateFieldSchema(annotation).ifPresent(fieldSchema -> {
                    schemaNode.set(field.getName(), fieldSchema);
                });
            }
            Class<?> fieldType = field.getType();
            // Check if the field type is a custom class within the application package
            if (fieldType.getName().startsWith(Application.class.getPackage().getName())) {
                var objectNode = generateObjectNode(fieldType);
                if (!objectNode.isEmpty()) {
                    schemaNode.set(field.getName(), generateObjectNode(fieldType));
                }
            }
        }
        return schemaNode;
    }

    /**
     * Generate the field schema based on the UISchema annotation.
     *
     * @param annotation the UISchema annotation
     * @return an Optional containing the generated ObjectNode if not empty, otherwise an empty Optional
     */
    private Optional<ObjectNode> generateFieldSchema(UISchema annotation) {
        ObjectNode fieldNode = JsonNodeFactory.instance.objectNode();
        if (!annotation.widget().equals(WidgetTypes.none)) {
            fieldNode.put("ui:widget", annotation.widget().name());
        }
        if (!annotation.placeholder().isEmpty()) {
            fieldNode.put("ui:placeholder", annotation.placeholder());
        }
        if (!annotation.help().isEmpty()) {
            fieldNode.put("ui:help", annotation.help());
        }
        if (!annotation.options().isEmpty()) {
            try {
                fieldNode.set("ui:options", new ObjectMapper().readTree(annotation.options()));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return Optional.ofNullable(!fieldNode.isEmpty() ? fieldNode : null);
    }

}
