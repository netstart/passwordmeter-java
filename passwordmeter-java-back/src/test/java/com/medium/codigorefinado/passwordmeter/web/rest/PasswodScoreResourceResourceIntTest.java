package com.medium.codigorefinado.passwordmeter.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;


import com.medium.codigorefinado.passwordmeter.PasswordmeterjavaApp;
import com.medium.codigorefinado.passwordmeter.service.PasswordMeterService;

/**
 * Test class for the PasswodScoreResource REST controller.
 *
 * @see PasswodScoreResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PasswordmeterjavaApp.class)
public class PasswodScoreResourceResourceIntTest {

	private MockMvc restMockMvc;

	@Autowired
	private PasswordMeterService passwordMeterSevice;
	
	private static final String PASSWORD_VERY_WEAK = "abc";
	private static final String PASSWORD_VERY_WEAK_COMPLEXITY = "Very Weak";
	private static final String PASSWORD_VERY_WEAK_PERCENT = "2%";
	
	
	private static final String PASSWORD_VERY_STRONG = "abSc5@akjaFHDF97&*^&6";
	private static final String PASSWORD_VERY_STRONG_COMPLEXITY = "Very Strong";
	private static final String PASSWORD_VERY_STRONG_PERCENT = "100%";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		PasswodScoreResourceResource passwodScoreResourceResource = new PasswodScoreResourceResource(
				passwordMeterSevice);
		restMockMvc = MockMvcBuilders.standaloneSetup(passwodScoreResourceResource).build();
	}

	
	@Test
	public void testDefaultAction() throws Exception {
		restMockMvc.perform(get("/api/passwod-score/check-password/xx"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testPasswodVeryWeak() throws Exception {
		restMockMvc.perform(get("/api/passwod-score/check-password/{passwod}", PASSWORD_VERY_WEAK))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.score").value(PASSWORD_VERY_WEAK_PERCENT))
			.andExpect(jsonPath("$.complexity").value(PASSWORD_VERY_WEAK_COMPLEXITY));
	}
	
	
	@Test
	public void testPasswodVeryStrong() throws Exception {
		restMockMvc.perform(get("/api/passwod-score/check-password/{passwod}", PASSWORD_VERY_STRONG))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.score").value(PASSWORD_VERY_STRONG_PERCENT))
			.andExpect(jsonPath("$.complexity").value(PASSWORD_VERY_STRONG_COMPLEXITY));
	}

}
