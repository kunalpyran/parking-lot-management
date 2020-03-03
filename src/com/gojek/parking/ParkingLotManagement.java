package com.gojek.parking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.gojek.parking.entities.Customer;
import com.gojek.parking.enums.ColorEnum;
import com.gojek.parking.enums.VehicleTypeEnum;
import com.gojek.parking.utils.Constants;

public class ParkingLotManagement {

	private final static String filePath = "//home//kunal//Pictures//workspace//parking-lot-management//src//input.txt";
	
	private int maxSize = 0;
	
	private Customer[] customerData = null;
	
	private List<Customer> customerHistory = null;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String query;
			ParkingLotManagement parking = null;
			while ((query = br.readLine()) != null) {
				if(Objects.nonNull(query)) {
					String[] queryData = query.split(" ");
					switch (queryData[0]) {
					case Constants.CREATE:
						if(queryData.length == 2) {
							parking = new ParkingLotManagement();
							parking.initialize(queryData[1]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.CREATE);
						}
						break;
					case Constants.PARK:
						if(queryData.length == 3) {
							parking.parkCar(queryData[1], queryData[2]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.PARK);
						}
						break;
					case Constants.LEAVE:
						if(queryData.length == 2) {
							parking.leaveCar(queryData[1]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.LEAVE);
						}
						break;
					case Constants.STATUS:
							parking.status();
						break;
					case Constants.REG_NO_BY_COLOR:
						if(queryData.length == 2) {
							parking.getResgistrationNumberByColor(queryData[1]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.REG_NO_BY_COLOR);
						}
						break;
					case Constants.SLOT_NO_BY_COLOR:
						if(queryData.length == 2) {
							parking.getSlotByColor(queryData[1]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.SLOT_NO_BY_COLOR);
						}
						break;
					case Constants.SLOT_NO_BY_REG:
						if(queryData.length == 2) {
							parking.getSlotByRegistrationNo(queryData[1]);
						} else {
							System.out.println("[ParkingLotManagement][Main] Invalid No of Parameters for " + Constants.SLOT_NO_BY_REG);
						}
						break;
					default:
						System.out.println("[ParkingLotManagement][Main] Invalid Input");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("[ParkingLotManagement][Main] Exception While Reading file");
		}
	}

	private void initialize(String size) {
		try {
			this.maxSize = Integer.valueOf(size);
			this.customerData = new Customer[maxSize];
			this.customerHistory = new ArrayList<Customer>();
			System.out.println("Created a parking lot with " + this.maxSize + " slots");
		} catch (NumberFormatException e) {
			System.out.println("[ParkingLotManagement][initialize] Invalid Parameters for " + Constants.CREATE);
		}
		
	}

	private void parkCar(String regNo, String color) {
		try {
			Customer customer = new Customer(VehicleTypeEnum.Car, regNo, ColorEnum.valueOf(color));
			int i = 0;
			for(; i<this.maxSize ; i++) {
				if(Objects.isNull(customerData[i])) {
					customer.getSlot().setSlotNumber(i+1);
					customer.setInTime(new Date().getTime());
					customerData[i] = customer;
					customerHistory.add(customer);
					System.out.println("Allocated slot number: " + (i+1));
					break;
				}
			}
			if(i == maxSize) {
				System.out.println("Sorry, parking lot is full");
			}
		} catch (Exception e) {
			System.out.println("[ParkingLotManagement][parkCar] Exception Occured in " + Constants.PARK + " : " + e);
		}
	}

	private void leaveCar(String slotNo) {
		try {
			int slot = Integer.valueOf(slotNo)-1;
			if(slot < this.maxSize) {
				Customer customer = customerData[slot];
				customer.setOutTime(new Date().getTime());
				customerData[slot] = null;
				System.out.println("Slot number " + slotNo + " is free");
			} else {
				System.out.println("Slot Number " + slotNo + " doesn't Exist");
			}
		} catch (Exception e) {
			System.out.println("[ParkingLotManagement][parkCar] Exception Occured in " + Constants.LEAVE + " : " + e);
		}
	}

	private void status() {
		System.out.println("Slot No.\tRegistration No\t\tColour");
		for(int i=0 ; i<this.maxSize ; i++) {
			if(Objects.nonNull(customerData[i])) {
				System.out.println((i+1)+ "\t\t" + customerData[i].getVehicle().getRegistrationNumber()
						+ "\t\t" + customerData[i].getVehicle().getColor());
			}
		}
	}

	private void getResgistrationNumberByColor(String colour) {
		try {
			ColorEnum color = ColorEnum.valueOf(colour);
			boolean vehicleFound = false;
			StringBuilder registrationNo = new StringBuilder();
			for(int i=0 ; i<this.maxSize ; i++) {
				if(Objects.nonNull(customerData[i]) && customerData[i].getVehicle().getColor().equals(color)) {
					vehicleFound = true;
					registrationNo.append(customerData[i].getVehicle().getRegistrationNumber() + ", ");
				}
			}
			String result = "Not found";
			if(vehicleFound) {
				result = registrationNo.substring(0, registrationNo.length()-2);
			}
			System.out.println(result);
		}catch (Exception e) {
			System.out.println("[ParkingLotManagement][parkCar] Exception Occured in " + Constants.REG_NO_BY_COLOR + " : " + e);
		}
	}

	private void getSlotByColor(String colour) {
		try {
			ColorEnum color = ColorEnum.valueOf(colour);
			boolean vehicleFound = false;
			StringBuilder slotNo = new StringBuilder();
			for(int i=0 ; i<this.maxSize ; i++) {
				if(Objects.nonNull(customerData[i]) && customerData[i].getVehicle().getColor().equals(color)) {
					vehicleFound = true;
					slotNo.append((i+1) + ", ");
				}
			}
			String result = "Not found";
			if(vehicleFound) {
				result = slotNo.substring(0, slotNo.length()-2);
			}
			System.out.println(result);
		}catch (Exception e) {
			System.out.println("[ParkingLotManagement][parkCar] Exception Occured in " + Constants.SLOT_NO_BY_COLOR + " : " + e);
		}		
	}

	private void getSlotByRegistrationNo(String registration) {
		try {
			int i = 0;
			for(;i<this.maxSize ; i++) {
				if(Objects.nonNull(customerData[i]) && customerData[i].getVehicle().getRegistrationNumber().equals(registration)) {
					System.out.println(i+1);
					break;
				}
			}
			if(i == maxSize)
				System.out.println("Not found");
		}catch (Exception e) {
			System.out.println("[ParkingLotManagement][parkCar] Exception Occured in " + Constants.SLOT_NO_BY_REG + " : " + e);
		}		
	}
}
