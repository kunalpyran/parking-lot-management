package com.gojek.parking.entities;

import com.gojek.parking.enums.ColorEnum;

public class Vehicle {

	private String registrationNumber;
	private ColorEnum color;
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public ColorEnum getColor() {
		return color;
	}
	public void setColor(ColorEnum color) {
		this.color = color;
	}
	
}
