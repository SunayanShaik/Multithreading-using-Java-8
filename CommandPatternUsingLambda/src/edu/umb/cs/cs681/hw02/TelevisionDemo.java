package edu.umb.cs.cs681.hw02;

public class TelevisionDemo {

	public static void main(String[] args) {
		
		TelevisionCommands tvCommands = new TelevisionCommands();
		
		Television tv = TelevisionRemote.getDevice();
		
		tvCommands.recordAction(tv :: on);
		tvCommands.recordAction(tv :: play);
		tvCommands.recordAction(tv :: increaseVolume);
		tvCommands.recordAction(tv :: decreaseVolume);
		tvCommands.recordAction(tv :: off);
		
		tvCommands.executeCommands();
		
	}
	
}