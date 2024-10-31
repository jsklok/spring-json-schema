package com.example.demo.system.validators;

import com.example.demo.system.utils.FormGenerator;
import com.example.demo.system.utils.JsonSchemaValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
public class ValidFormValidator implements ConstraintValidator<ValidForm, Map<String, Object>> {

    //
    private final Logger log = LoggerFactory.getLogger(ValidFormValidator.class);

    //
    private final ValidForm annotation;

    /**
     * Constructor
     *
     * @param annotation annotation instance
     */
    public ValidFormValidator(ValidForm annotation) {
        this.annotation = annotation;
    }

    /**
     *
     * @param values object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(Map<String, Object> values, ConstraintValidatorContext context) {
        log.trace("Validating schema...");
        var form = FormGenerator.getForm(annotation.schema());
        var assertions = JsonSchemaValidator.validate(form.jsonSchema(), values);
        if (!assertions.isEmpty()) {
            log.trace("schema validation failed");
            context.disableDefaultConstraintViolation();
            assertions.forEach(a -> {
                context.buildConstraintViolationWithTemplate(a.getMessage())
                    .addPropertyNode(a.getProperty())
                    .addConstraintViolation();
            });
        } else {
            log.trace("schema validation passed");
            return true;
        }
        return false;
    }
}
