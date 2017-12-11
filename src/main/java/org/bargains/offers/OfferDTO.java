package org.bargains.offers;

import lombok.Data;
import org.bargains.config.validators.ValidCurrency;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
class OfferDTO {
    @NotEmpty
    private String description;
    @NotNull
    @Valid
    private Money price;
    @NotNull
    private Instant offerStarts;
    @NotNull
    private Instant offerEnds;

    Offer toOffer() {
        return Offer.builder()
                .description(description)
                .price(org.javamoney.moneta.Money.of(price.amount, price.currency))
                .offerStarts(offerStarts)
                .offerEnds(offerEnds)
                .description(description)
                .description(description)
                .build();
    }

    @Data
    private static class Money {
        @NotEmpty
        @ValidCurrency
        private String currency;
        @NotNull
        private Double amount;
    }
}