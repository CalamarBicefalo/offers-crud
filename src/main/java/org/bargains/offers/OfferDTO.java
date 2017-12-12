package org.bargains.offers;

import lombok.Data;
import org.bargains.config.validators.ValidCurrency;
import org.bargains.config.validators.ValidDouble;
import org.bargains.config.validators.ValidInstant;
import org.hibernate.validator.constraints.NotEmpty;
import org.javamoney.moneta.Money;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
class OfferDTO {
    @NotEmpty
    private String description;
    @NotNull
    @Valid
    private Price price;
    @NotEmpty
    @ValidInstant
    private String offerStarts;
    @NotEmpty
    @ValidInstant
    private String offerEnds;

    Offer toOffer() {
        return Offer.builder()
                .description(description)
                .price(price.toMoney())
                .offerStarts(Instant.parse(offerStarts))
                .offerEnds(Instant.parse(offerEnds))
                .description(description)
                .description(description)
                .build();
    }

    @Data
    private static class Price {
        @NotEmpty
        @ValidCurrency
        private String currency;
        @ValidDouble
        @NotNull
        private String amount;

        private Money toMoney() {
            return Money.of(Double.parseDouble(amount), currency);
        }
    }
}