package com.example.demo.system.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 * @param message
 * @param errors
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMessage(
    String message,
    List<ErrorItem> errors
) {
    public record ErrorItem(
        String field,
        String message
    ) {
    }
}
