File Utils - THIS PROJECT DOES NOT HAVE A NAME YET, SO, I CALL IT FILE UTILS.

It is a tool that helps you to convert positional txt files to Object and Object to positional files based on annotations:



package br.com.wehavescience.utils.sample;

import br.com.wehavescience.utils.file.annotations.Field;

/**
 * @author gabriel
 * 
 * Oct 10, 2013
 */
public class ModelSample {
	public static final String LINE_SAMPLE = "THISISMYMODELOFMYEXAMPLE                                                                                                       ";

	@Field(firstPosition = 1, lastPosition = 4)
	private String firstWord;
	@Field(firstPosition = 5, lastPosition = 6)
	private String secondWord;
	@Field(firstPosition = 7, lastPosition = 8)
	private String thirdWord;
	@Field(firstPosition = 9, lastPosition = 13)
	private String fourthWord;
	@Field(firstPosition = 14, lastPosition = 15)
	private String fifthWord;
	@Field(firstPosition = 16, lastPosition = 17)
	private String sixthWord;
	@Field(firstPosition = 18, lastPosition = 50)
	private String seventhWord;

	public String getFirstWord() {
		return firstWord;
	}

	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}

	public String getSecondWord() {
		return secondWord;
	}

	public void setSecondWord(String secondWord) {
		this.secondWord = secondWord;
	}

	public String getThirdWord() {
		return thirdWord;
	}

	public void setThirdWord(String thirdWord) {
		this.thirdWord = thirdWord;
	}

	public String getFourthWord() {
		return fourthWord;
	}

	public void setFourthWord(String fourthWord) {
		this.fourthWord = fourthWord;
	}

	public String getFifthWord() {
		return fifthWord;
	}

	public void setFifthWord(String fifthWord) {
		this.fifthWord = fifthWord;
	}

	public String getSixthWord() {
		return sixthWord;
	}

	public void setSixthWord(String sixthWord) {
		this.sixthWord = sixthWord;
	}

	public String getSeventhWord() {
		return seventhWord;
	}

	public void setSeventhWord(String seventhWord) {
		this.seventhWord = seventhWord;
	}

	@Override
	public String toString() {
		return "ModelSample [firstWord=" + firstWord + ", secondWord="
				+ secondWord + ", thirdWord=" + thirdWord + ", fourthWord="
				+ fourthWord + ", fifthWord=" + fifthWord + ", sixthWord="
				+ sixthWord + ", seventhWord=" + seventhWord + "]";
	}
}
