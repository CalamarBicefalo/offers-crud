package org.bargains.validators;

import org.bargains.config.validators.ValidDoubleValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidDoubleValidatorTest {

    ValidDoubleValidator subject = new ValidDoubleValidator();

    @Test
    public void valid_whenValid_returnsTrue() {
        assertThat(subject.isValid("29.95", null)).isTrue();
    }

    @Test
    public void valid_whenNull_returnsTrue() {
        assertThat(subject.isValid(null, null)).isTrue();
    }

    @Test
    public void valid_whenFunkyDouble_returnsFalse() {
        assertThat(subject.isValid("not valid stuff", null)).isFalse();
    }

    @Test
    public void valid_whenPartialDouble_returnsFalse() {
        assertThat(subject.isValid("29.95.97", null)).isFalse();
    }
}