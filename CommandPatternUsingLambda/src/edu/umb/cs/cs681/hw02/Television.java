package edu.umb.cs.cs681.hw02;

public class Television implements Device {

	private int volume = 10;
	
	@Override
	public void on() {
		System.out.println("Television is TURNED ON");
	}

	@Override
	public void off() {
		System.out.println("Television is TURNED OFF");
	}

	@Override
	public void increaseVolume() {
		volume++;
		System.out.println("Increased Volume is : " + volume);
	}

	@Override
	public void decreaseVolume() {
		volume--;
		System.out.println("Decreased Volume is: " + volume);
	}

	@Override
	public void play() {
		System.out.println("Default Volume is: " + volume);
		System.out.println("Television is playing video......");
	}

}