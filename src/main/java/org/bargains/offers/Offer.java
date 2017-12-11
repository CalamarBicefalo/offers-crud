package org.bargains.offers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.hateoas.Identifiable;

import java.time.Instant;

@Data
@Builder
class Offer implements Identifiable<String> {

    @JsonIgnore
    private String id;

    private String title;
    private String description;

    private Money price;

    private Instant offerStarts;
    private Instant offerEnds;

    @JsonIgnore
    private boolean cancelled;

    @JsonIgnore
    public boolean isActive() {
        Instant now = Instant.now();
        return !cancelled
                && now.isAfter(offerStarts)
                && now.isBefore(offerEnds);
    }
}
