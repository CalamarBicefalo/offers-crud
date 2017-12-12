package org.bargains;

import lombok.Builder;
import lombok.Data;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An e2e journey for our api
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OffersE2eJourney {

    @Test
    public void merchantJourney() {
        val offersUrl = getE2eOffersUrlFromIndex();

        createE2eOffer(offersUrl, ACTIVE_OFFER);
        E2eOffer cancelledOffer = createE2eOffer(offersUrl, CANCELLED_OFFER);
        createE2eOffer(offersUrl, EXPIRED_OFFER);

        cancelOffer(cancelledOffer);

        val offers = retrieveE2eOffers(offersUrl);

        val activeOffer = findOffer(offers, "active");
        cancelledOffer = findOffer(offers, "cancelled");
        val expiredOffer = findOffer(offers, "expired");

        assertThat(cancelledOffer.getCancelLink().isPresent()).isFalse();
        assertThat(expiredOffer.getCancelLink().isPresent()).isFalse();
        assertThat(activeOffer.getCancelLink().isPresent()).isTrue();

        assertThat(cancelledOffer.getRedeemLink().isPresent()).isFalse();
        assertThat(expiredOffer.getRedeemLink().isPresent()).isFalse();
        assertThat(activeOffer.getRedeemLink().isPresent()).isTrue();

        assertThat(offers).containsExactlyInAnyOrder(
                activeOffer,
                cancelledOffer,
                expiredOffer);

    }

    private void cancelOffer(E2eOffer offer) {
        LinkedMultiValueMap params = new LinkedMultiValueMap();
        restTemplate.patchForObject(offer.getCancelLink().get(), params, Map.class);
    }

    private E2eOffer findOffer(List<E2eOffer> offers, String description) {
        return offers.stream()
                .filter(o -> o.description.equals(description))
                .findFirst().get();
    }

    private static class OfferList extends ArrayList<E2eOffer> {
    }

    private List<E2eOffer> retrieveE2eOffers(String offersUrl) {
        return restTemplate.getForObject(offersUrl, OfferList.class);
    }

    private E2eOffer createE2eOffer(String offersUrl, E2eOffer offer) {
        return restTemplate.postForObject(offersUrl, offer, E2eOffer.class);
    }

    private String getE2eOffersUrlFromIndex() {
        return (String) ((Map) restTemplate.getForObject("/", ArrayList.class).get(0)).get("href");
    }

    @LocalServerPort
    private int localPort;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;

    @Before
    public void configureRestTemplate() throws Exception {
        restTemplate = this.restTemplateBuilder
                .rootUri(String.format("http://localhost:%s", localPort))
                .defaultMessageConverters()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    private static final E2eOffer ACTIVE_OFFER = E2eOffer.builder()
            .description("active")
            .price(Money.builder().amount(29.95).currency("EUR").build())
            .offerStarts("2017-10-01T10:12:13Z")
            .offerEnds("3017-10-01T10:12:13Z")
            .build();

    private static final E2eOffer CANCELLED_OFFER = E2eOffer.builder()
            .description("cancelled")
            .price(Money.builder().amount(29.95).currency("EUR").build())
            .offerStarts("2017-10-01T10:12:13Z")
            .offerEnds("3017-10-01T10:12:13Z")
            .build();


    private static final E2eOffer EXPIRED_OFFER = E2eOffer.builder()
            .description("expired")
            .price(Money.builder().amount(29.95).currency("EUR").build())
            .offerStarts("2017-10-01T10:12:13Z")
            .offerEnds("2017-10-01T10:12:13Z")
            .build();

    @Data
    @Builder
    private static class Link {
        private String rel;
        private String href;
    }

    @Data
    @Builder
    private static class Money {
        private double amount;
        private String currency;
    }

    @Data
    @Builder
    private static class E2eOffer {
        private String description;

        private Money price;

        private String offerStarts;
        private String offerEnds;

        private List<Link> links;

        public Optional<String> getCancelLink() {
            return extractLink("cancel");
        }

        public Optional<String> getRedeemLink() {
            return extractLink("redeem");
        }

        private Optional<String> extractLink(String rel) {
            if (links == null) {
                return Optional.empty();
            }
            return links.stream()
                    .filter((link -> link.getRel().equals(rel)))
                    .map(link -> link.getHref())
                    .findFirst();
        }
    }
}
