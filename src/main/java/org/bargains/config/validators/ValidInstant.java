package org.bargains.config.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidInstantValidator.class)
public @interface ValidInstant {

    String message() default "Invalid instant, try something like '2017-03-15T10:11:23Z' instead";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
