package org.bargains.offers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.hateoas.Identifiable;

import java.time.Instant;

@Data
@Builder
public class Offer implements Identifiable<String>, Cancellable {

    @JsonIgnore
    private String id;

    private String description;

    private Money price;

    private Instant offerStarts;
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

    @Override
    public void cancel() {
        this.cancelled = true;
    }
}
