package com.example.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Account  implements Serializable,Cloneable {

	private static final long serialVersionUID = 6250200424323417716L;

	Boolean activeCard;
	
	Integer availableLimit;
	
	String[] violations;

	public Account() {}
	
	
	public Account(Boolean activeCard, Integer availableLimit, String[] violations) {
		super();
		this.activeCard = activeCard;
		this.availableLimit = availableLimit;
		this.violations = violations;
	}

	public Boolean getActiveCard() {
		return activeCard;
	}

	public void setActiveCard(Boolean activeCard) {
		this.activeCard = activeCard;
	}

	public Integer getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(Integer availableLimit) {
		this.availableLimit = availableLimit;
	}

	public String[] getViolations() {
		return violations;
	}


	public void setViolations(String[] violations) {
		this.violations = violations;
	}

	
	
	@Override
	public Account clone() {
		String[] violations = this.violations == null ? new String[] {} :  this.violations;
		return new Account(this.activeCard, this.availableLimit, violations);
	}
	
}
