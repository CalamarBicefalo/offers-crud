package org.bargains.offers;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferTest {

    @Test
    public void isActive_whenNowUTCBetweenOfferValidity_returnsTrue() {
        Instant futureDate = ZonedDateTime.now(UTC).plusDays(1).toInstant();
        Offer nonExpiredOffer = Offer.builder()
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(futureDate)
                .build();

        assertThat(nonExpiredOffer.isActive()).isTrue();
    }

    @Test
    public void isActive_whenNowUTCAfterOfferEnds_returnsFalse() {
        Instant aSecondAgo = ZonedDateTime.now(UTC).minusSeconds(1).toInstant();
        Offer nonExpiredOffer = Offer.builder()
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(aSecondAgo)
                .build();

        assertThat(nonExpiredOffer.isActive()).isFalse();
    }

    @Test
    public void isActive_whenNowUTCBeforeOfferStarts_returnsFalse() {
        Instant tomorrow = ZonedDateTime.now(UTC).plusDays(1).toInstant();
        Offer nonExpiredOffer = Offer.builder()
                .offerStarts(tomorrow)
                .offerEnds(ZonedDateTime.now().plusYears(999).toInstant()) // We'll be well dead by then
                .build();

        assertThat(nonExpiredOffer.isActive()).isFalse();
    }

    @Test
    public void isActive_whenOfferHasBeenCancelled_returnsFalse() {
        Instant futureDate = ZonedDateTime.now(UTC).plusDays(1).toInstant();
        Offer nonExpiredOffer = Offer.builder()
                .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
                .offerEnds(futureDate)
                .cancelled(true)
                .build();

        assertThat(nonExpiredOffer.isActive()).isFalse();
    }
}