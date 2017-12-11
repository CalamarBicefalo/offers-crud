package org.bargains;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void index_returnsOffersEndpoint() throws Exception {
        mvc.perform(request(GET, ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rel", is("offers")))
                .andExpect(jsonPath("$[0].href", endsWith("/offers")))
        ;
    }

    @Test
    public void indexSlash_returnsOffersEndpoint() throws Exception {
        mvc.perform(request(GET, "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rel", is("offers")))
                .andExpect(jsonPath("$[0].href", endsWith("/offers")))
        ;
    }

}