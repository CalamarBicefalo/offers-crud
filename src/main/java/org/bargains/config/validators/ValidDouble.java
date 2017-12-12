package org.bargains.config.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDoubleValidator.class)
public @interface ValidDouble {

    String message() default "Invalid number, try something like '29.95' instead";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
