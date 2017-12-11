package org.bargains.offers;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OffersControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private OffersService offersService;

	@Test
	public void offerCreation_whenValidOffer_returns201AndCreatesOffer() throws Exception {
		Offer expectedOffer = Offer.builder()
				.title("offer")
				.description("A life-changing opportunity relevant in our consumerist world")
				.price(Money.of(29.95, "EUR"))
				.offerStarts(LocalDateTime.of(2017, Month.NOVEMBER,1,10,10,12))
				.offerEnds(LocalDateTime.of(2018, Month.NOVEMBER,1,10,10,12))
				.build();
		mvc.perform(request(POST, "/offers")
				.contentType(APPLICATION_JSON)
				.content("{" +
						"  \"title\": \"offer\"," +
						"  \"description\": \"A life-changing opportunity relevant in our consumerist world\"," +
						"  \"price\": {" +
						"    \"amount\": 29.95," +
						"    \"currency\": \"EUR\"" +
						"  }," +
						"  \"offerStarts\": \"2017-11-01T10:10:12\"," +
						"  \"offerEnds\": \"2018-11-01T10:10:12\"" +
						"}"))

				.andExpect(status().isCreated());

        verify(offersService).create(expectedOffer);
	}

}
