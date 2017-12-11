package org.bargains.offers;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Data
@Builder
class Offer {

    private String title;
    private String description;

    private Money price;

    private LocalDateTime offerStarts;
    private LocalDateTime offerEnds;

    private boolean cancelled;
    private boolean active;

}
