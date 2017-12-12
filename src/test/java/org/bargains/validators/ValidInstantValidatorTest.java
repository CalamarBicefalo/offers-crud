package org.bargains.validators;

import org.bargains.config.validators.ValidInstantValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidInstantValidatorTest {

    ValidInstantValidator subject = new ValidInstantValidator();

    @Test
    public void valid_whenValid_returnsTrue() {
        assertThat(subject.isValid("2017-03-15T10:11:23Z", null)).isTrue();
    }

    @Test
    public void valid_whenNull_returnsTrue() {
        assertThat(subject.isValid(null, null)).isTrue();
    }

    @Test
    public void valid_whenFunkyDate_returnsFalse() {
        assertThat(subject.isValid("not valid stuff", null)).isFalse();
    }

    @Test
    public void valid_whenPartialInstant_returnsFalse() {
        assertThat(subject.isValid("2017-03-15", null)).isFalse();
    }
}