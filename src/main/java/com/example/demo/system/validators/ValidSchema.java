package com.example.demo.system.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 *
 */
@Documented
@Constraint(validatedBy = ValidSchemaValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSchema {

    String message() default "schema is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
