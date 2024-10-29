package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Repository
public class TemplateRepository {
    // --------------------------------------------------------------------------------------------

    //
    private final Map<String, String> templates;

    /**
     *
     */
    public TemplateRepository() {
        templates = Map.of(
            "postgresql", """
                {
                  "type": "object",
                  "properties": {
                    "first_name": {
                      "type": "string",
                      "minLength": 3
                    },
                    "last_name": {
                      "type": "string",
                      "minLength": 3
                    }
                  },
                  "required": [
                    "first_name",
                    "last_name"
                  ]
                }
                """,
            "mongodb", """
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
                """,
            "mariadb", """
                {
                  "type": "object",
                  "properties": {
                    "pass1": {
                      "title": "Password",
                      "type": "string",
                      "minLength": 3
                    },
                    "pass2": {
                      "title": "Repeat password",
                      "type": "string",
                      "minLength": 3
                    },
                    "age": {
                      "title": "Age",
                      "type": "number",
                      "minimum": 18
                    }
                  }
                }
                """
        );
    }

    /**
     * @param templateId
     * @return
     */
    public Optional<String> getTemplate(String templateId) {
        return Optional.ofNullable(templates.get(templateId));
    }

}
