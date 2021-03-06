package org.bargains.offers;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;


public class InMemoryOffersServiceTest {

    private OffersService offersService = new InMemoryOffersService();

    @Test
    public void create_persistsOffer() {
        Offer offer = Offer.builder()
                .description("A life-changing opportunity relevant in our consumerist world")
                .price(Money.of(29.95, "EUR"))
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .build();

        offersService.create(offer);

        assertThat(offersService.findAll()).contains(offer);
    }

    @Test
    public void create_returnsOfferWithAssignedId() {
        Offer offer = Offer.builder()
                .description("A life-changing opportunity relevant in our consumerist world")
                .price(Money.of(29.95, "EUR"))
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .build();

        offer = offersService.create(offer);

        assertThat(offer.getId()).isNotEmpty();
    }

    @Test
    public void cancel_whenActiveOffer_makesOfferInactive() {
        Offer offer = Offer.builder()
                .description("A life-changing opportunity relevant in our consumerist world")
                .price(Money.of(29.95, "EUR"))
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(ZonedDateTime.now().plusDays(5).toInstant())
                .build();
        offer = offersService.create(offer);
        assertThat(offer.isActive()).isTrue();

        offersService.cancel(offer.getId());

        assertThat(offersService.findAll().get(0).isActive()).isFalse();
    }
}