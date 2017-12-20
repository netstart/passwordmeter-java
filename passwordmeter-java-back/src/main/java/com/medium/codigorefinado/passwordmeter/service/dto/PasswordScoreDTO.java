package com.medium.codigorefinado.passwordmeter.service.dto;

public class PasswordScoreDTO {

	public String score = "0 %";
	public String complexity = ":(";

	public PasswordScoreDTO() {
	}

	public PasswordScoreDTO(String score, String complexity) {
		this.score = score;
		this.complexity = complexity;
	}

	@Override
	public String toString() {
		return "PasswordScoreDTO [score=" + score + ", complexity=" + complexity + "]";
	}

}
