package org.bargains.offers;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;


public class OffersServiceTest {

    private OffersService offersService = new InMemoryOfferService();

    @Test
    public void create_persistsOffer() {
        Offer offer = Offer.builder()
                .title("offer")
                .description("A life-changing opportunity relevant in our consumerist world")
                .price(Money.of(29.95, "EUR"))
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .build();

        offersService.create(offer);

        assertThat(offersService.findAll()).contains(offer);
    }
}