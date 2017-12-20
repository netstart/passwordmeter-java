package com.medium.codigorefinado.passwordmeter.service;

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
	
	public PasswordScoreDTO checkPassword(String password) throws ScriptException, NoSuchMethodException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval("String.prototype.strReverse = function() {\r\n" + 
				"	var newstring = \"\";\r\n" + 
				"	for (var s=0; s < this.length; s++) {\r\n" + 
				"		newstring = this.charAt(s) + newstring;\r\n" + 
				"	}\r\n" + 
				"	return newstring;\r\n" + 
				"};\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"function chkPass(pwd) {\r\n" + 
				"	var oScorebar = '';\r\n" + 
				"	var oScore = '';\r\n" + 
				"	var oComplexity = '';\r\n" + 
				"	// Simultaneous variable declaration and value assignment aren't supported in IE apparently\r\n" + 
				"	// so I'm forced to assign the same value individually per var to support a crappy browser *sigh* \r\n" + 
				"	var nScore=0, nLength=0, nAlphaUC=0, nAlphaLC=0, nNumber=0, nSymbol=0, nMidChar=0, nRequirements=0, nAlphasOnly=0, nNumbersOnly=0, nUnqChar=0, nRepChar=0, nRepInc=0, nConsecAlphaUC=0, nConsecAlphaLC=0, nConsecNumber=0, nConsecSymbol=0, nConsecCharType=0, nSeqAlpha=0, nSeqNumber=0, nSeqSymbol=0, nSeqChar=0, nReqChar=0, nMultConsecCharType=0;\r\n" + 
				"	var nMultRepChar=1, nMultConsecSymbol=1;\r\n" + 
				"	var nMultMidChar=2, nMultRequirements=2, nMultConsecAlphaUC=2, nMultConsecAlphaLC=2, nMultConsecNumber=2;\r\n" + 
				"	var nReqCharType=3, nMultAlphaUC=3, nMultAlphaLC=3, nMultSeqAlpha=3, nMultSeqNumber=3, nMultSeqSymbol=3;\r\n" + 
				"	var nMultLength=4, nMultNumber=4;\r\n" + 
				"	var nMultSymbol=6;\r\n" + 
				"	var nTmpAlphaUC=\"\", nTmpAlphaLC=\"\", nTmpNumber=\"\", nTmpSymbol=\"\";\r\n" + 
				"	var sAlphaUC=\"0\", sAlphaLC=\"0\", sNumber=\"0\", sSymbol=\"0\", sMidChar=\"0\", sRequirements=\"0\", sAlphasOnly=\"0\", sNumbersOnly=\"0\", sRepChar=\"0\", sConsecAlphaUC=\"0\", sConsecAlphaLC=\"0\", sConsecNumber=\"0\", sSeqAlpha=\"0\", sSeqNumber=\"0\", sSeqSymbol=\"0\";\r\n" + 
				"	var sAlphas = \"abcdefghijklmnopqrstuvwxyz\";\r\n" + 
				"	var sNumerics = \"01234567890\";\r\n" + 
				"	var sSymbols = \")!@#$%^&*()\";\r\n" + 
				"	var sComplexity = \"Too Short\";\r\n" + 
				"	var sStandards = \"Below\";\r\n" + 
				"	var nMinPwdLen = 8;\r\n" + 
				"	var nd = 0;\r\n" + 
				"	if (pwd) {\r\n" + 
				"		nScore = parseInt(pwd.length * nMultLength);\r\n" + 
				"		nLength = pwd.length;\r\n" + 
				"		var arrPwd = pwd.replace(/\\s+/g,\"\").split(/\\s*/);\r\n" + 
				"		var arrPwdLen = arrPwd.length;\r\n" + 
				"		\r\n" + 
				"		/* Loop through password to check for Symbol, Numeric, Lowercase and Uppercase pattern matches */\r\n" + 
				"		for (var a=0; a < arrPwdLen; a++) {\r\n" + 
				"			if (arrPwd[a].match(/[A-Z]/g)) {\r\n" + 
				"				if (nTmpAlphaUC !== \"\") { if ((nTmpAlphaUC + 1) == a) { nConsecAlphaUC++; nConsecCharType++; } }\r\n" + 
				"				nTmpAlphaUC = a;\r\n" + 
				"				nAlphaUC++;\r\n" + 
				"			}\r\n" + 
				"			else if (arrPwd[a].match(/[a-z]/g)) { \r\n" + 
				"				if (nTmpAlphaLC !== \"\") { if ((nTmpAlphaLC + 1) == a) { nConsecAlphaLC++; nConsecCharType++; } }\r\n" + 
				"				nTmpAlphaLC = a;\r\n" + 
				"				nAlphaLC++;\r\n" + 
				"			}\r\n" + 
				"			else if (arrPwd[a].match(/[0-9]/g)) { \r\n" + 
				"				if (a > 0 && a < (arrPwdLen - 1)) { nMidChar++; }\r\n" + 
				"				if (nTmpNumber !== \"\") { if ((nTmpNumber + 1) == a) { nConsecNumber++; nConsecCharType++; } }\r\n" + 
				"				nTmpNumber = a;\r\n" + 
				"				nNumber++;\r\n" + 
				"			}\r\n" + 
				"			else if (arrPwd[a].match(/[^a-zA-Z0-9_]/g)) { \r\n" + 
				"				if (a > 0 && a < (arrPwdLen - 1)) { nMidChar++; }\r\n" + 
				"				if (nTmpSymbol !== \"\") { if ((nTmpSymbol + 1) == a) { nConsecSymbol++; nConsecCharType++; } }\r\n" + 
				"				nTmpSymbol = a;\r\n" + 
				"				nSymbol++;\r\n" + 
				"			}\r\n" + 
				"			/* Internal loop through password to check for repeat characters */\r\n" + 
				"			var bCharExists = false;\r\n" + 
				"			for (var b=0; b < arrPwdLen; b++) {\r\n" + 
				"				if (arrPwd[a] == arrPwd[b] && a != b) { /* repeat character exists */\r\n" + 
				"					bCharExists = true;\r\n" + 
				"					/* \r\n" + 
				"					Calculate icrement deduction based on proximity to identical characters\r\n" + 
				"					Deduction is incremented each time a new match is discovered\r\n" + 
				"					Deduction amount is based on total password length divided by the\r\n" + 
				"					difference of distance between currently selected match\r\n" + 
				"					*/\r\n" + 
				"					nRepInc += Math.abs(arrPwdLen/(b-a));\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"			if (bCharExists) { \r\n" + 
				"				nRepChar++; \r\n" + 
				"				nUnqChar = arrPwdLen-nRepChar;\r\n" + 
				"				nRepInc = (nUnqChar) ? Math.ceil(nRepInc/nUnqChar) : Math.ceil(nRepInc); \r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		/* Check for sequential alpha string patterns (forward and reverse) */\r\n" + 
				"		for (var s=0; s < 23; s++) {\r\n" + 
				"			var sFwd = sAlphas.substring(s,parseInt(s+3));\r\n" + 
				"			var sRev = sFwd.strReverse();\r\n" + 
				"			if (pwd.toLowerCase().indexOf(sFwd) != -1 || pwd.toLowerCase().indexOf(sRev) != -1) { nSeqAlpha++; nSeqChar++;}\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		/* Check for sequential numeric string patterns (forward and reverse) */\r\n" + 
				"		for (var s=0; s < 8; s++) {\r\n" + 
				"			var sFwd = sNumerics.substring(s,parseInt(s+3));\r\n" + 
				"			var sRev = sFwd.strReverse();\r\n" + 
				"			if (pwd.toLowerCase().indexOf(sFwd) != -1 || pwd.toLowerCase().indexOf(sRev) != -1) { nSeqNumber++; nSeqChar++;}\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		/* Check for sequential symbol string patterns (forward and reverse) */\r\n" + 
				"		for (var s=0; s < 8; s++) {\r\n" + 
				"			var sFwd = sSymbols.substring(s,parseInt(s+3));\r\n" + 
				"			var sRev = sFwd.strReverse();\r\n" + 
				"			if (pwd.toLowerCase().indexOf(sFwd) != -1 || pwd.toLowerCase().indexOf(sRev) != -1) { nSeqSymbol++; nSeqChar++;}\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"	/* Modify overall score value based on usage vs requirements */\r\n" + 
				"\r\n" + 
				"		/* General point assignment */\r\n" + 
				"		if (nAlphaUC > 0 && nAlphaUC < nLength) {	\r\n" + 
				"			nScore = parseInt(nScore + ((nLength - nAlphaUC) * 2));\r\n" + 
				"			sAlphaUC = \"+ \" + parseInt((nLength - nAlphaUC) * 2); \r\n" + 
				"		}\r\n" + 
				"		if (nAlphaLC > 0 && nAlphaLC < nLength) {	\r\n" + 
				"			nScore = parseInt(nScore + ((nLength - nAlphaLC) * 2)); \r\n" + 
				"			sAlphaLC = \"+ \" + parseInt((nLength - nAlphaLC) * 2);\r\n" + 
				"		}\r\n" + 
				"		if (nNumber > 0 && nNumber < nLength) {	\r\n" + 
				"			nScore = parseInt(nScore + (nNumber * nMultNumber));\r\n" + 
				"			sNumber = \"+ \" + parseInt(nNumber * nMultNumber);\r\n" + 
				"		}\r\n" + 
				"		if (nSymbol > 0) {	\r\n" + 
				"			nScore = parseInt(nScore + (nSymbol * nMultSymbol));\r\n" + 
				"			sSymbol = \"+ \" + parseInt(nSymbol * nMultSymbol);\r\n" + 
				"		}\r\n" + 
				"		if (nMidChar > 0) {	\r\n" + 
				"			nScore = parseInt(nScore + (nMidChar * nMultMidChar));\r\n" + 
				"			sMidChar = \"+ \" + parseInt(nMidChar * nMultMidChar);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		\r\n" + 
				"		/* Point deductions for poor practices */\r\n" + 
				"		if ((nAlphaLC > 0 || nAlphaUC > 0) && nSymbol === 0 && nNumber === 0) {  // Only Letters\r\n" + 
				"			nScore = parseInt(nScore - nLength);\r\n" + 
				"			nAlphasOnly = nLength;\r\n" + 
				"			sAlphasOnly = \"- \" + nLength;\r\n" + 
				"		}\r\n" + 
				"		if (nAlphaLC === 0 && nAlphaUC === 0 && nSymbol === 0 && nNumber > 0) {  // Only Numbers\r\n" + 
				"			nScore = parseInt(nScore - nLength); \r\n" + 
				"			nNumbersOnly = nLength;\r\n" + 
				"			sNumbersOnly = \"- \" + nLength;\r\n" + 
				"		}\r\n" + 
				"		if (nRepChar > 0) {  // Same character exists more than once\r\n" + 
				"			nScore = parseInt(nScore - nRepInc);\r\n" + 
				"			sRepChar = \"- \" + nRepInc;\r\n" + 
				"		}\r\n" + 
				"		if (nConsecAlphaUC > 0) {  // Consecutive Uppercase Letters exist\r\n" + 
				"			nScore = parseInt(nScore - (nConsecAlphaUC * nMultConsecAlphaUC)); \r\n" + 
				"			sConsecAlphaUC = \"- \" + parseInt(nConsecAlphaUC * nMultConsecAlphaUC);\r\n" + 
				"		}\r\n" + 
				"		if (nConsecAlphaLC > 0) {  // Consecutive Lowercase Letters exist\r\n" + 
				"			nScore = parseInt(nScore - (nConsecAlphaLC * nMultConsecAlphaLC)); \r\n" + 
				"			sConsecAlphaLC = \"- \" + parseInt(nConsecAlphaLC * nMultConsecAlphaLC);\r\n" + 
				"		}\r\n" + 
				"		if (nConsecNumber > 0) {  // Consecutive Numbers exist\r\n" + 
				"			nScore = parseInt(nScore - (nConsecNumber * nMultConsecNumber));  \r\n" + 
				"			sConsecNumber = \"- \" + parseInt(nConsecNumber * nMultConsecNumber);\r\n" + 
				"		}\r\n" + 
				"		if (nSeqAlpha > 0) {  // Sequential alpha strings exist (3 characters or more)\r\n" + 
				"			nScore = parseInt(nScore - (nSeqAlpha * nMultSeqAlpha)); \r\n" + 
				"			sSeqAlpha = \"- \" + parseInt(nSeqAlpha * nMultSeqAlpha);\r\n" + 
				"		}\r\n" + 
				"		if (nSeqNumber > 0) {  // Sequential numeric strings exist (3 characters or more)\r\n" + 
				"			nScore = parseInt(nScore - (nSeqNumber * nMultSeqNumber)); \r\n" + 
				"			sSeqNumber = \"- \" + parseInt(nSeqNumber * nMultSeqNumber);\r\n" + 
				"		}\r\n" + 
				"		if (nSeqSymbol > 0) {  // Sequential symbol strings exist (3 characters or more)\r\n" + 
				"			nScore = parseInt(nScore - (nSeqSymbol * nMultSeqSymbol)); \r\n" + 
				"			sSeqSymbol = \"- \" + parseInt(nSeqSymbol * nMultSeqSymbol);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		\r\n" + 
				"		/* Determine complexity based on overall score */\r\n" + 
				"		if (nScore > 100) { nScore = 100; } else if (nScore < 0) { nScore = 0; }\r\n" + 
				"		if (nScore >= 0 && nScore < 20) { sComplexity = \"Very Weak\"; }\r\n" + 
				"		else if (nScore >= 20 && nScore < 40) { sComplexity = \"Weak\"; }\r\n" + 
				"		else if (nScore >= 40 && nScore < 60) { sComplexity = \"Good\"; }\r\n" + 
				"		else if (nScore >= 60 && nScore < 80) { sComplexity = \"Strong\"; }\r\n" + 
				"		else if (nScore >= 80 && nScore <= 100) { sComplexity = \"Very Strong\"; }\r\n" + 
				"		\r\n" + 
				"		/* Display updated score criteria to client */\r\n" + 
				"		print(nScore + \"%\");\r\n" + 
				"		print(sComplexity);\r\n" + 
				"		\r\n" + 
				"		var result = {score:  nScore + \"%\", complexity:  sComplexity};\r\n" + 
				"		\r\n" + 
				"		return result;\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"	else {\r\n" + 
				"		/* Display default score criteria to client */\r\n" + 
				"		print(0 + \"%\");\r\n" + 
				"		print(\":(\" );\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"}");
			
			Invocable invocable = (Invocable) engine;
			Bindings  result = (Bindings) invocable.invokeFunction("chkPass", password);
			
			PasswordScoreDTO passwordScore  = new PasswordScoreDTO(result.get("score").toString(), result.get("complexity").toString());

			log.debug("passwod score: {} ", passwordScore);
			
			return passwordScore;
	}

	public static void main(String[] args) {
		

	}

}
