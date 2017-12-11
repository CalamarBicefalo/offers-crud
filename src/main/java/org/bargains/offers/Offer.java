package org.bargains.offers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.javamoney.moneta.Money;
import org.springframework.hateoas.Identifiable;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
public class Offer implements Identifiable<String> {

    @JsonIgnore
    private String id;

    @NotEmpty
    private String description;

    @NotNull
    private Money price;

    @NotNull
    private Instant offerStarts;
    @NotNull
    private Instant offerEnds;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean cancelled;

    @JsonIgnore
    public boolean isActive() {
        Instant now = Instant.now();
        return !cancelled
                && now.isAfter(offerStarts)
                && now.isBefore(offerEnds);
    }

    public void cancel() {
        this.cancelled = true;
    }
}
