package com.example.demo;

import com.example.demo.uischema.UISchema;
import com.example.demo.uischema.WidgetTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 *
 * @param model
 * @param year
 * @param color
 */
public record Car(

    @UISchema(help = "The car model")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    String model,

    @UISchema(widget = WidgetTypes.range, help = "The car year", options = "{\"unit\": \"year\"}")
    @Min(1900)
    @Max(2030)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    int year,


    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    String color,

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    Engine engine

) {

    /**
     *
     * @param type
     * @param horsepower
     */
    public record Engine(

        @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
        String type,

        @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
        int horsepower

    ) {
    }
}
