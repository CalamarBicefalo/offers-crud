package org.bargains.offers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Data
@Builder
class Offer {

    @JsonIgnore
    private Long id;

    private String title;
    private String description;

    private Money price;

    private LocalDateTime offerStarts;
    private LocalDateTime offerEnds;

    private boolean cancelled;
    private boolean active;

}
