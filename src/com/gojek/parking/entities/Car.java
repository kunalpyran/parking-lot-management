package com.gojek.parking.entities;

import com.gojek.parking.enums.ColorEnum;

public class Car extends Vehicle {

	private final int slotSize = 2;

	public Car(String regNo, ColorEnum color) {
		this.setRegistrationNumber(regNo);
		this.setColor(color);
	}
	
	public int getSlotSize() {
		return slotSize;
	}
	
}
