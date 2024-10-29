package com.example.demo.system.validators;

import com.example.demo.controllers.TemplateRequest;
import com.example.demo.system.utils.JsonSchemaValidator;
import com.example.demo.repositories.TemplateRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ValidSchemaValidator implements ConstraintValidator<ValidSchema, TemplateRequest> {

    //
    private final Logger log = LoggerFactory.getLogger(ValidSchemaValidator.class);

    //
    private final TemplateRepository templateRepository;

    /**
     *
     * @param templateRepository
     */
    public ValidSchemaValidator(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     *
     * @param request object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(TemplateRequest request, ConstraintValidatorContext context) {
        log.trace("Validating schema...");
        log.trace("template: {}", request.templateId());
        var templateSchema = templateRepository.getTemplate(request.templateId()).orElse(null);
        if (templateSchema != null) {
            var assertions = JsonSchemaValidator.validate(templateSchema, request.values());
            if (!assertions.isEmpty()) {
                log.trace("schema validation failed");
                context.disableDefaultConstraintViolation();
                assertions.forEach(a -> {
                    context.buildConstraintViolationWithTemplate(a.getMessage())
                        .addPropertyNode(a.getProperty())
                        .addConstraintViolation();
                });
            }
            else {
                log.trace("schema validation passed");
                return true;
            }
        }
        return false;
    }
}
