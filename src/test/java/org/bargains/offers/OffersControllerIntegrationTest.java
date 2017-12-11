package org.bargains.offers;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OffersControllerIntegrationTest extends AbstractIntegrationTest {

    @Before
    public void validOfferCreationReturnsValidOfferWithId() {
        when(offersService.create(eq(EXPECTED_VALID_OFFER)))
                .thenReturn(EXPECTED_VALID_OFFER_WITH_ID);
    }

    @Test
    public void offerCreation_whenValidOffer_returns201AndCreatesOffer() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content(VALID_OFFER))

                .andExpect(status().isCreated());

        verify(offersService).create(EXPECTED_VALID_OFFER);
    }

    @Test
    public void offerCreation_whenValidOffer_returnsCancellationLink() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content(VALID_OFFER))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.links[?(@.rel=='cancel')].href",
                        contains(containsString("/offers"))));
    }

    @Test
    public void offerCancellation_whenCreatingOfferAndFollowingCancelLink_cancelsOffer() throws Exception {
        MockHttpServletResponse creationResponse = mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content(VALID_OFFER))
                .andReturn().getResponse();

        mvc.perform(request(PATCH, extractLink(creationResponse, "cancel").get()))
                .andExpect(status().isNoContent());
        verify(offersService).cancel(EXPECTED_VALID_OFFER_WITH_ID.getId());
    }

    @Test
    public void offerCancellation_whenExpiredOffer_doesNotIncludeCancelLink() throws Exception {
        when(offersService.create(eq(EXPECTED_VALID_OFFER)))
                .thenReturn(Offer.builder().cancelled(true).build());

        MockHttpServletResponse creationResponse = mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content(VALID_OFFER))
                .andReturn().getResponse();

        Optional<String> cancelLink = extractLink(creationResponse, "cancel");

        assertThat(cancelLink.isPresent()).isFalse();
    }

    private Optional<String> extractLink(MockHttpServletResponse response, String rel) throws UnsupportedEncodingException {
        JSONArray urls = JsonPath.read(response.getContentAsString(), String.format("$.links[?(@.rel=='%s')].href", rel));
        return Optional.ofNullable(urls.isEmpty() ? null : (String) urls.get(0));
    }

    @MockBean
    private OffersService offersService;

    private static final String VALID_OFFER = "{" +
            "  \"title\": \"offer\"," +
            "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
            "  \"price\": {" +
            "    \"amount\": 29.95," +
            "    \"currency\": \"EUR\"" +
            "  }," +
            "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
            "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
            "}";

    private static final Offer EXPECTED_VALID_OFFER = Offer.builder()
            .title("offer")
            .description("A life-changing opportunity relevant in our consumerist world")
            .price(Money.of(29.95, "EUR"))
            .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .build();

    // This would be so much better with kotlin :'(
    private static final Offer EXPECTED_VALID_OFFER_WITH_ID = Offer.builder()
            .id("a-very-unique-id")
            .title("offer")
            .description("A life-changing opportunity relevant in our consumerist world")
            .price(Money.of(29.95, "EUR"))
            .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .build();
}
