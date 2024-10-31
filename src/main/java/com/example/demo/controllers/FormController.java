package com.example.demo.controllers;

import com.example.demo.Car;
import com.example.demo.system.utils.FormGenerator;
import com.example.demo.system.validators.ValidForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/form")
@Validated
public class FormController {

    /**
     *
     * @return
     */
    @GetMapping
    public FormGenerator.Form getForm() {
        return FormGenerator.getForm(Car.class);
    }

    /**
     *
     * @return
     */
    @PostMapping
    public String validate(
        @RequestBody @Valid FormGenerator.Form form) {
        return "valid";
    }

    /**
     *
     */
    public record CarRequest(

        @NotNull
        @ValidForm(schema = Car.class)
        @JsonProperty("values")
        Map<String, Object> values

    ) {
    }

}
