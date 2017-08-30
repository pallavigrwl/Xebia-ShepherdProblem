package com.yak.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Stock {
	
	private Double milk;
	private Integer skins;
	private int elapsedTime;
	public Double getMilk() {
		return milk;
	}
	public void setMilk(Double milk) {
		this.milk = milk;
	}
	public Integer getSkins() {
		return skins;
	}
	public void setSkins(Integer skins) {
		this.skins = skins;
	}
	public int getElapsedTime() {
		return elapsedTime;
	}
	@JsonIgnore
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

}
