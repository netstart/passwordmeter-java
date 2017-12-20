package com.medium.codigorefinado.passwordmeter.web.rest;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medium.codigorefinado.passwordmeter.service.PasswordMeterService;
import com.medium.codigorefinado.passwordmeter.service.dto.PasswordScoreDTO;

/**
 * PasswodScoreResource controller
 */
@RestController
@RequestMapping("/api/passwod-score")
public class PasswodScoreResourceResource {

	private final Logger log = LoggerFactory.getLogger(PasswodScoreResourceResource.class);

	private final PasswordMeterService passwordMeterSevice;

	public PasswodScoreResourceResource(PasswordMeterService passwordMeterSevice) {
		this.passwordMeterSevice = passwordMeterSevice;
	}

	@GetMapping("/check-password/{password}")
	public PasswordScoreDTO checkPassword(@PathVariable String password) throws NoSuchMethodException, ScriptException {
//		PasswordScoreDTO passwordScore = new PasswordScoreDTO("10 %", "Good");
//		return passwordScore;

		return this.passwordMeterSevice.checkPassword(password);
	}

}
