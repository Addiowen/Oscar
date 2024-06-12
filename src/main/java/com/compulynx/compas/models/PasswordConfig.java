package com.compulynx.compas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passwords")
public class PasswordConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "pass_expiry")
	private int passExpiry;
	@Column(name = "has_numeric")
	private Boolean hasNumeric;
	@Column(name = "has_specialchar")
	private Boolean hasSpecialChar;
	@Column(name = "has_uppercase")
	private Boolean hasUpperCase;
	@Column(name = "has_lowercase")
	private Boolean lowerCase;
	@Column(name = "has_letters")
	private Boolean hasLetters;
	@Column(name = "char_length")
	private Integer charLength;

	public PasswordConfig() {
	}

	public PasswordConfig(int id, int passExpiry, Boolean hasNumeric, Boolean hasSpecialChar, Boolean hasUpperCase,
			Boolean lowerCase, Boolean hasLetters, int charLength) {
		super();
		this.id = id;
		this.passExpiry = passExpiry;
		this.hasNumeric = hasNumeric;
		this.hasSpecialChar = hasSpecialChar;
		this.hasUpperCase = hasUpperCase;
		this.lowerCase = lowerCase;
		this.hasLetters = hasLetters;
		this.charLength = charLength;
	}

	public int getId() {
		return id;
	}

	public Integer getCharLength() {
		return charLength;
	}

	public void setCharLength(Integer charLength) {
		this.charLength = charLength;
	}

	public int getPassExpiry() {
		return passExpiry;
	}

	public void setPassExpiry(int passExpiry) {
		this.passExpiry = passExpiry;
	}

	public Boolean getHasNumeric() {
		return hasNumeric;
	}

	public void setHasNumeric(Boolean hasnuMeric) {
		this.hasNumeric = hasnuMeric;
	}

	public Boolean getHasLetters() {
		return hasLetters;
	}

	public void setHasLetters(Boolean hasLetters) {
		this.hasLetters = hasLetters;
	}

	public Boolean getHasUpperCase() {
		return hasUpperCase;
	}

	public void setHasUpperCase(Boolean hasUpperCase) {
		this.hasUpperCase = hasUpperCase;
	}

	public Boolean getLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(Boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

	public Boolean getHasSpecialChar() {
		return hasSpecialChar;
	}

	public void setHasSpecialChar(Boolean hasSpecialChar) {
		this.hasSpecialChar = hasSpecialChar;
	}

	@Override
	public String toString() {
		return "PasswordConfig [id=" + id + ", passExpiry=" + passExpiry + ", hasnuMeric=" + hasNumeric
				+ ", hasLetters=" + hasLetters + ", hasUpperCase=" + hasUpperCase + ", lowerCase=" + lowerCase
				+ ", hasSpecialChar=" + hasSpecialChar + "]";
	}

}
