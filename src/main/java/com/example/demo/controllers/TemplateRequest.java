package com.example.demo.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 *
 */
public record TemplateRequest(

    @NotBlank
    @JsonProperty("template_id")
    String templateId,

    @NotNull
    @JsonProperty("values")
    Map<String, Object> values

) {
}
