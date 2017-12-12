package org.bargains.config.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class ValidInstantValidator implements ConstraintValidator<ValidInstant, String> {

    @Override
    public void initialize(ValidInstant constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        try {
            Instant.parse(value);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
