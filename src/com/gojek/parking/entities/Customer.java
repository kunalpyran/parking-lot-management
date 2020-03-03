package com.gojek.parking.entities;

import java.util.UUID;

import com.gojek.parking.enums.ColorEnum;
import com.gojek.parking.enums.VehicleTypeEnum;

public class Customer {

	private Vehicle vehicle;
	private VehicleTypeEnum vehicleType;
	private String ticketNo;
	private Slot slot;
	private long inTime;
	private long outTime;
	
	public Customer(VehicleTypeEnum type, String regNo, ColorEnum color){
		this.slot = new Slot();
		if(type.equals(VehicleTypeEnum.Car)) {
			this.vehicle = new Car(regNo, color);
			this.slot.setSlotNumber(((Car)this.vehicle).getSlotSize());
		}
		this.vehicleType = type;
		this.ticketNo = UUID.randomUUID().toString();
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public VehicleTypeEnum getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleTypeEnum vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	public long getInTime() {
		return inTime;
	}
	public void setInTime(long inTime) {
		this.inTime = inTime;
	}
	public long getOutTime() {
		return outTime;
	}
	public void setOutTime(long outTime) {
		this.outTime = outTime;
	}
	
}
