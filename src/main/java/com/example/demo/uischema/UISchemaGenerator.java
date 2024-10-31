package com.example.demo.uischema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 *
 */
public class UISchemaGenerator {

    /**
     * @param targetType
     * @return
     */
    public ObjectNode generateSchema(Type targetType) {
        ObjectNode schemaNode = JsonNodeFactory.instance.objectNode();
        Class<?> clazz = (Class<?>) targetType;
        for (var field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UISchema.class)) {
                var annotation = field.getAnnotation(UISchema.class);
                generateFieldSchema(annotation).ifPresent(fieldSchema -> {
                    schemaNode.set(field.getName(), fieldSchema);
                });
            }
        }
        return schemaNode;
    }

    /**
     * @param annotation
     * @return
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
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return Optional.ofNullable(!fieldNode.isEmpty() ? fieldNode : null);
    }

}
