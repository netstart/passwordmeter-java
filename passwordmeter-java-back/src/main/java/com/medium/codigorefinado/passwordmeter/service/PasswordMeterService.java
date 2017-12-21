package com.medium.codigorefinado.passwordmeter.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medium.codigorefinado.passwordmeter.service.dto.PasswordScoreDTO;

@Service
public class PasswordMeterService {
	private final Logger log = LoggerFactory.getLogger(PasswordMeterService.class);


	public PasswordScoreDTO checkPassword(String password)
			throws ScriptException, NoSuchMethodException, FileNotFoundException {

		InputStream is = getClass().getResourceAsStream("password-meter.js"); //arquivo .js está no resources
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(inputStreamReader);

		Invocable invocable = (Invocable) engine;
		Bindings result = (Bindings) invocable.invokeFunction("chkPass", password);

		PasswordScoreDTO passwordScore = new PasswordScoreDTO(result.get("score").toString(),
				result.get("complexity").toString());

		log.debug("passwod score: {} ", passwordScore);

		return passwordScore;
	}

}
