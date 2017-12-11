package org.bargains.config.validators;

import org.javamoney.moneta.Money;

import javax.money.UnknownCurrencyException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public void initialize(ValidCurrency constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        try {
            Money.of(0, value);
        } catch (UnknownCurrencyException e) {
            return false;
        }
        return true;
    }
}
