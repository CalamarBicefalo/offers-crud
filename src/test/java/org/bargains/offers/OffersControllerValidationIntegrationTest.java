package org.bargains.offers;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OffersControllerValidationIntegrationTest {

    @Test
    public void offerCreation_whenValidOffer_returns201() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void offerCreation_whenMissingDescription_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("description")));
    }

    @Test
    public void offerCreation_whenMissingPrice_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("price")));
    }

    @Test
    public void offerCreation_whenMissingPriceAmount_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("price.amount")));
    }

    @Test
    public void offerCreation_whenMissingPriceCurrency_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95" +
                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("price.currency")));
    }

    @Test
    public void offerCreation_whenUnknownPriceCurrency_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"post-brexit worthless pounds\"" +

                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("price.currency")));
    }

    @Test
    public void offerCreation_whenMissingOfferStarts_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("offerStarts")));
    }

    @Test
    public void offerCreation_whenInvalidOfferStarts_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerStarts\": \"aquirkydate\"," +
                        "  \"offerEnds\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("offerStarts")));
    }

    @Test
    public void offerCreation_whenMissingOfferEnds_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerStarts\": \"2017-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("offerEnds")));
    }

    @Test
    public void offerCreation_whenInvalidOfferEnds_returns400() throws Exception {
        mvc.perform(request(POST, "/offers")
                .contentType(APPLICATION_JSON)
                .content("{" +
                        "  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
                        "  \"price\": {" +
                        "    \"amount\": 29.95," +
                        "    \"currency\": \"EUR\"" +
                        "  }," +
                        "  \"offerEnds\": \"aquirkydate\"," +
                        "  \"offerStarts\": \"2018-11-01T10:10:12Z\"" +
                        "}"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("offerEnds")));
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OffersService offersService;

    @Before
    public void validOfferCreationReturnsValidOfferWithId() {
        when(offersService.create(any()))
                .thenReturn(EXPECTED_VALID_OFFER);
    }

    private static final Offer EXPECTED_VALID_OFFER = Offer.builder()
            .description("A life-changing opportunity relevant in our consumerist world")
            .price(Money.of(29.95, "EUR"))
            .offerStarts(LocalDateTime.of(2017, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .offerEnds(LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 10, 12).atZone(UTC).toInstant())
            .build();
}
