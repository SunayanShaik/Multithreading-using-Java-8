package edu.umb.cs.cs681.hw03;

public class Car {

	private int price;
	private int year;
	private float mileage;
	private String makerName;

	public Car(int price, int year, float mileage, String makerName) {
		this.price = price;
		this.year = year;
		this.mileage = mileage;
		this.makerName = makerName;
	}

	public int getPrice() {
		return price;
	}

	public int getYear() {
		return year;
	}

	public float getMileage() {
		return mileage;
	}
	
	public String getMakerName() {
		return makerName;
	}

}
