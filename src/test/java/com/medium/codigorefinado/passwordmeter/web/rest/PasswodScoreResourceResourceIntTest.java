package com.medium.codigorefinado.passwordmeter.web.rest;

import com.medium.codigorefinado.passwordmeter.PasswordmeterjavaApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the PasswodScoreResource REST controller.
 *
 * @see PasswodScoreResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasswordmeterjavaApp.class)
public class PasswodScoreResourceResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PasswodScoreResourceResource passwodScoreResourceResource = new PasswodScoreResourceResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(passwodScoreResourceResource)
            .build();
    }

    /**
    * Test defaultAction
    */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/passwod-score-resource/default-action"))
            .andExpect(status().isOk());
    }

}
