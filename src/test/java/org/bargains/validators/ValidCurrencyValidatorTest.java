package org.bargains.validators;

import org.bargains.config.validators.ValidCurrencyValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidCurrencyValidatorTest {

    ValidCurrencyValidator subject = new ValidCurrencyValidator();

    @Test
    public void valid_whenValid_returnsTrue() {
        assertThat(subject.isValid("EUR", null)).isTrue();
    }

    @Test
    public void valid_whenNull_returnsTrue() {
        assertThat(subject.isValid(null, null)).isTrue();
    }

    @Test
    public void valid_whenFunkyCode_returnsFalse() {
        assertThat(subject.isValid("post-brexit worthless pounds", null)).isFalse();
    }
}